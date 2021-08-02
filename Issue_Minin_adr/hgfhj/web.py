import os
import pymysql
from flask import *
from werkzeug.utils import secure_filename

app=Flask(__name__)
con=pymysql.connect(host="localhost",port=3306,user="root",password="",db="Issue_Mining")
cmd=con.cursor()
path=r"./static/applications"
from flask.json import jsonify


@app.route('/registration',methods=['post'])
def registration():
    try:
        first_name=request.form['fname']
        second_name=request.form['sname']
        gender=request.form['gen']
        dob=request.form['dob']
        ward=request.form['wrd']
        place=request.form['pla']
        post=request.form['pst']
        pin=request.form['pin']
        district=request.form['dst']
        phone=request.form['phn']
        email=request.form['email']
        password=request.form['pass']
        cmd.execute("insert into login values(null,'"+phone+"','"+password+"','user')")
        id=con.insert_id()
        cmd.execute("insert into user values(null,'"+str(id)+"','"+first_name+"','"+second_name+"','"+gender+"','"+dob+"','"+ward+"','"+place+"','"+post+"','"+pin+"','"+district+"','"+phone+"','"+email+"')")
        con.commit()
        return jsonify({'task':"Successfull"})
    except:
        return '''<script>alert("username is already exist,please choose another");
        </script>'''



@app.route('/application',methods=['post'])
def application():
    subject=request.form['sub']
    application=request.files['files']
    print(application)
    appli=secure_filename(application.filename)
    application.save(os.path.join(path,appli))
    uid=request.form['uid']
    cmd.execute("insert into user_application values(null,'"+str(uid)+"','"+subject+"','"+appli+"',curdate(),'pending')")
    con.commit()
    return jsonify({'task':"Successfull"})

@app.route('/chat',methods=['post'])
def chat():
    fid=request.form['from_id']
    print(fid,"fid")
    tid=request.form['toid']
    print(tid,"tid")
    message=request.form['msg']
    print(message)
    cmd.execute("insert into chat values(null,'"+str(fid)+"','"+str(tid)+"','"+message+"',curdate())")
    con.commit()
    print("okkkkkkkkkkkkkkkkkk")
    return jsonify("success")


@app.route('/Viewchat',methods=['get','Post'])
def Viewchat():
    uid = request.form['uid']
    fid = request.form['fid']
    cmd.execute("select * from chat where (fid='" + str(uid) + "' and tid='" + str(fid) + "') or (fid='" + str(fid) + "' and tid='" + str(uid) + "') order by id")
    row_headers = [x[0] for x in cmd.description]
    s = cmd.fetchall()
    json_data = []
    for result in s:
        json_data.append(dict(zip(row_headers, result)))
        con.commit()
        print(json_data)
    return jsonify(json_data)


@app.route('/view_councilor',methods=['get','Post'])
def view_councilor():
    cmd.execute("select * from councilor")
    row_headers = [x[0] for x in cmd.description]
    s = cmd.fetchall()
    json_data = []
    for result in s:
        json_data.append(dict(zip(row_headers, result)))
        con.commit()
        print(json_data)
    return jsonify(json_data)


@app.route('/feedback',methods=['post'])
def feedback():
    department=request.form['dpt']
    feedback=request.form['fdbk']
    uid=request.form['uid']
    cmd.execute("insert into feedback values(null,'"+str(uid)+"',curdate(),'"+department+"','"+feedback+"')")
    con.commit()
    return jsonify({'task':"Successfull"})


@app.route('/policies',methods=['post'])
def policies():
    cmd.execute("select * from policy")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        row = []
        for r in result:
            row.append(str(r))
        json_data.append(dict(zip(row_headers, row)))
    con.commit()
    print(json_data)
    return jsonify(json_data)


@app.route('/issues',methods=['get','post'])
def issues():
    ward=request.form['wrd']
    print(ward)
    issue=request.form['isu']
    print(issue)
    description=request.form['dscn']
    print(description)
    uid=request.form['uid']
    print(uid)
    cmd.execute("insert into issues values(null,'"+str(uid)+"','"+ward+"','"+issue+"','"+description+"')")
    con.commit()
    return jsonify({'task':"success"})

@app.route('/rating',methods=['post'])
def rating():
    department=request.form['dpt']
    print(department)
    rating=request.form['rating']
    print(rating)
    uid=request.form['uid']
    print(uid)
    cmd.execute("insert into rating values(null,'"+str(uid)+"','"+department+"','"+rating+"')")
    con.commit()
    return jsonify({'task':"success"})


@app.route('/notification',methods=['post'])
def notification():
    cmd.execute("select * from notification")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        row = []
        for r in result:
            row.append(str(r))
        json_data.append(dict(zip(row_headers, row)))
    con.commit()
    print(json_data)
    return jsonify(json_data)


@app.route('/about',methods=['post'])
def about():
    cmd.execute("select * from about_corporation")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        row = []
        for r in result:
            row.append(str(r))
        json_data.append(dict(zip(row_headers, row)))
    con.commit()
    print(json_data)
    return jsonify(json_data)



@app.route('/department',methods=['post'])
def department():
    cmd.execute("SELECT`department`.*,`login`.`type`FROM`login`INNER JOIN`department`ON`department`.`lid`=`login`.`id`WHERE`login`.`type`!='Deactive'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        row = []
        for r in result:
            row.append(str(r))
        json_data.append(dict(zip(row_headers, row)))
    con.commit()
    print(json_data)
    return jsonify(json_data)


@app.route('/rating_department',methods=['post'])
def rating_department():
    cmd.execute("SELECT`department`.*,`login`.`type`FROM`login`INNER JOIN`department`ON`department`.`lid`=`login`.`id`WHERE`login`.`type`!='Deactive'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        row = []
        for r in result:
            row.append(str(r))
        json_data.append(dict(zip(row_headers, row)))
    con.commit()
    print(json_data)
    return jsonify(json_data)


@app.route('/log',methods=['POST'])
def log():
    username=request.form['uname']
    password=request.form['pass']
    cmd.execute("select * from login where username='"+username+"' and password='"+password+"'")
    s=cmd.fetchone()
    if s is None :

        return jsonify({'task':"Invalid"})

    elif s[3]=="user":

        return jsonify({'task':str(s[0])})


app.run(host='0.0.0.0')