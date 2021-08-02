import MySQLdb
from flask import *
from werkzeug.utils import secure_filename

from hgfhj.topic_modelling2 import Topic_modellingg

app=Flask(__name__)
con=MySQLdb.connect(host="localhost",port=3306,user="root",password="",db="Issue_Mining")
cmd=con.cursor()
app.secret_key='abc'

@app.route('/')
def sib():
     return render_template('login.html')

@app.route('/aboutcorportion')
def aboutcorportion():
    return render_template('Admin/About Corporation.html')

@app.route('/information',methods=['post'])
def information():
    corporation=request.form['textfield']
    information=request.form['textarea']
    cmd.execute("insert into about_corporation values(null,'"+corporation+"','"+information+"')")
    con.commit()
    return '''<script>alert("Process successfull")
                window.location="/aboutcorportion"
                </script>'''

@app.route('/adminhome')
def adminhome():
    return render_template('Admin/Admin Home.html')


@app.route('/Edit_councilor')
def Edit_councilor():
        id = request.args.get('id')
        session['id'] = id
        cmd.execute("select * from councilor where cid='"+id+"'")
        s=cmd.fetchone()
        return render_template('admin/Edit_councilor.html',val=s)



@app.route('/delete_councilor')
def delete_councilor():
    id = request.args.get('id')
    cmd.execute("update login set type='Deactive' where id='"+id+"'")
    con.commit()
    return '''<script>alert("Delete successfully")
              window.location="/Councilordetails"
              </script>'''


@app.route('/update_councilor', methods=['post'])
def update_councilor():
    lid = session['id']
    first_name = request.form['textfield']
    second_name = request.form['textfield2']
    gender=request.form['RadioGroup1']
    DOB = request.form['textfield3']
    ward = request.form['textfield4']
    phone_no = request.form['textfield5']
    email = request.form['textfield6']
    place = request.form['textfield7']
    post = request.form['textfield8']
    pin = request.form['textfield9']
    cmd.execute("update councilor set first_name='"+first_name+"',second_name='"+second_name+"',gender='"+gender+"',DOB='"+DOB+"',ward='"+ward+"',phone_no ='"+phone_no +"',email='"+email+"',place='"+place+"',post='"+post+"',pin='"+pin+"' where cid='"+lid+"'")
    con.commit()
    return '''<script>alert("update successfull")
               window.location="/Councilordetails"
               </script>'''


@app.route('/Councilordetails')
def Councilordetails():
    cmd.execute("SELECT councilor.*,`login`.`type` FROM `login` JOIN`councilor` ON `login`.`id`=`councilor`.`lid`")
    s = cmd.fetchall()
    return render_template('Admin/Councilor Details.html',val=s)


@app.route('/Councilorregistration',methods=['post'])
def Councilorregistration():
    return render_template('Admin/Councilor Registration.html')


@app.route('/DepartmentDetails')
def DepartmentDetails():
    cmd.execute("SELECT`department`.*,`login`.`type`FROM`login`INNER JOIN`department`ON`department`.`lid`=`login`.`id`")
    s = cmd.fetchall()
    return render_template('Admin/Department Details.html',val=s)


@app.route('/DepartmentRegistration',methods=['post'])
def DepartmentRegistration():
    return render_template('Admin/Department Registration.html')


@app.route('/edit_mayor')
def edit_mayor():
        id=request.args.get('id')
        session['id']=id
        cmd.execute("select * from mayor where mid='"+id+"'")
        s = cmd.fetchone()
        return render_template('Admin/Edit_mayor.html',val=s)


@app.route('/delete_mayor')
def delete_mayor():
    id=request.args.get('id')
    cmd.execute("update login set type='Deactive' where id='"+id+"'")
    con.commit()
    return '''<script>alert("Delete successfully")
            window.location="/MayorDetails"
            </script>'''



@app.route('/update_mayor',methods=['post'])
def update_mayor():
    lid = session['id']
    year = request.form['select']
    first_name = request.form['textfield']
    second_name = request.form['textfield2']
    Gender=request.form['RadioGroup1']
    DOB = request.form['textfield3']
    Qualification = request.form['select2']
    phone_no = request.form['textfield4']
    Email = request.form['textfield5']
    cmd.execute("update mayor set year='"+year+"',first_name='"+first_name+"',second_name='"+second_name+"',Gender='"+Gender+"',DOB='"+DOB+"',Qualification='"+Qualification+"',phone_no='"+phone_no+"',Email='"+Email+"' where mid='"+lid+"'")
    con.commit()
    return '''<script>alert("update successfull")
            window.location="/MayorDetails"
            </script>'''


@app.route('/MayorDetails')
def MayorDetails():
    import datetime
    now= datetime.datetime.now()
    yr=now.year
    print(yr)
    cmd.execute("SELECT `mayor`.*,`login`.`type`FROM`login`INNER JOIN`mayor`ON`mayor`.`lid`=`login`.`id`")
    s= cmd.fetchall()
    return render_template('Admin/Mayor Details.html',val=s,year=str(yr))


@app.route('/MayorRegistration',methods=['post'])
def MayorRegistration():
    return render_template('Admin/Mayor Registration.html')


@app.route('/view_mayor')
def view_mayor():
    return render_template('Admin/View Mayor Details.html')


@app.route('/search',methods=['post'])
def search():
    year=request.form['select']
    cmd.execute("select * from mayor where year='"+year+"'")
    s=cmd.fetchall()
    return render_template('Admin/View Mayor Details.html',val=s)


@app.route('/StaffHome')
def StaffHome():
    return render_template('Staff/Staff Home.html')


@app.route('/clk_policy')
def clk_policy():
    cmd.execute("SELECT`policy`.*,`mayor`.`first_name`,`mayor`.`second_name`FROM`mayor`INNER JOIN`policy`ON`policy`.`lid`=`mayor`.`lid`")
    s=cmd.fetchall()
    return render_template('Staff/Policy.html',val=s)


@app.route('/SubmitReport')
def SubmitReport():
        id=request.args.get('id')
        session['wid']=id
        return render_template('Staff/Submit Report.html')


@app.route('/sub_report',methods=['post'])
def sub_report():
    title=request.form['textfield']
    report=request.files['file']
    fn=secure_filename(report.filename)
    report.save("static/Reports/"+fn)
    cmd.execute("SELECT `did` FROM `staff` WHERE `lid`='"+str(session['id'])+"'")
    did= cmd.fetchone()
    cmd.execute("insert into report values(null,'"+str(did[0])+"','"+title+"',curdate(),'"+fn+"','"+str(session['wid'])+"')")
    con.commit()
    return '''<script>alert("Process Successfull")
              window.location="/SubmitReport"
              </script>'''


@app.route('/ViewApplication')
def ViewApplication():
    cmd.execute("SELECT `assign_work`.*,`user_application`.`application`FROM`user_application`INNER JOIN `assign_work`ON`assign_work`.`applctid`=`user_application`.`id` where cid='"+str(session['id'])+"'")
    s=cmd.fetchall()
    return render_template('Staff/View Application.html',val=s)


@app.route('/separate_chat')
def separate_chat():
    return render_template('councilor/Separate_chat.html')


@app.route('/mayors_chat')
def Chat():
    cmd.execute("SELECT * FROM mayor join login on login.id=mayor.lid where login.type!='Deactive'")
    s=cmd.fetchall()
    return render_template('Councilor/Mayors_Chat.html',val=s)


@app.route('/users_chat')
def users_chat():
    cmd.execute("select * from user join login on login.id=user.lid where login.type!='Deactive'")
    s=cmd.fetchall()
    return render_template('Councilor/Users_chat.html', val=s)


@app.route('/councilors_chat')
def councilors_chat():
    id=session['id']
    cmd.execute("SELECT * FROM councilor join login on login.id=councilor.lid where login.type!='Deactive' and login.id!='"+str(id)+"'")
    s=cmd.fetchall()
    return render_template('Councilor/Councilors_Chat.html', val=s)


@app.route('/councilor_interaction')
def councilor_interaction():
        id=request.args.get('id')
        session['pid'] = id
        fromid=session['id']
        cmd.execute("SELECT `fid`,`message` FROM `chat` WHERE (`fid`='" + str(fromid) + "' AND `tid`='" + str(id) + "') OR (`fid`='" + str(id) + "' AND `tid`='"+str(fromid)+"')")
        s = cmd.fetchall()
        return render_template('councilor/chating.html',id=str(id),data=s)


@app.route('/send_chat',methods=['post'])
def send_chat():
    message=request.form['text']
    fromid = session['id']
    toid=session['pid']
    cmd.execute("insert into chat values(null,'"+str(fromid)+"','"+str(toid)+"','"+message+"',curdate())")
    con.commit()

    fromid = session['id']
    id=session['pid']
    cmd.execute("SELECT `fid`,`message` FROM `chat` WHERE (`fid`='" + str(fromid) + "' AND `tid`='" + str(id) + "') OR (`fid`='" + str(id) + "' AND `tid`='"+str(fromid)+"')")
    s = cmd.fetchall()
    return render_template('councilor/chating.html',id=str(id),data=s)


@app.route('/Notification')
def Notification():
    return render_template('Councilor/Notifications.html')


@app.route('/send_notification',methods=['post'])
def send_notification():
    notification=request.form['textarea']
    cmd.execute("insert into notification values(null,'"+str(session['id'])+"',curdate(),'"+notification+"')")
    con.commit()
    return '''<script>alert("Process Successfull")
             window.location="/Notification"
             </script>'''


@app.route('/councilor_policy')
def councilor_policy():
    cmd.execute("SELECT`policy`.*,`mayor`.`first_name`,`mayor`.`second_name`FROM`mayor`INNER JOIN`policy`ON`policy`.`lid`=`mayor`.`lid`")
    s=cmd.fetchall()
    return render_template('councilor/Policy.html',val=s)


@app.route('/Complaint')
def Complaint():
    id = session['id']
    print(id)
    cmd.execute("select * from complaint where lid='" +str(id)+ "'")
    s = cmd.fetchall()
    print(s)
    return render_template('Councilor/Complaint.html',val=s)


@app.route('/send_complaint',methods=['post'])
def send_complaint():
    complaint=request.form['textarea']
    cmd.execute("insert into complaint values(null,'"+str(session['id'])+"',curdate(),'"+complaint+"','pending')")
    con.commit()
    return '''<script>alert("process successfull")
                window.location="/Complaint"
                </script>'''


@app.route('/CouncilorHome')
def CouncilorHome():
    return render_template('Councilor/Councilor Home.html')


@app.route('/frequent_issues')
def frequent_issues():
    cmd.execute("SELECT `ward` FROM `councilor` WHERE `lid`='"+str(session['id'])+"'")
    wrd= cmd.fetchone()
    cmd.execute("SELECT`issues`.*,`user`.`first_name`,`user`.`second_name`FROM`user`INNER JOIN`issues`ON`issues`.`lid`=`user`.`lid`  where issues.ward='"+str(wrd[0])+"'")
    s=cmd.fetchall()
    try:

        ob = Topic_modellingg()
        res = ob.Topic_modell(s)
        print("res--", res)
        print(len(res))
        res_len = len(res)
        mine_res = []
        for i in range(0, res_len):
            print(i)
            # res_remove= res[i].replace(',', '')
            # print("remmmmm----",res_remove)
            cmd.execute("select * from mine_result where mine_result='" + str(res[i]) + "'")
            ss = cmd.fetchone()
            if ss is None:
                cmd.execute("insert into mine_result values(null,'" + str(session['id']) + "','" + str(res[i]) + "')")
                con.commit()
                mine_res.append(res[i])
                # print("mine---",mine_res)

                # print(str(mine_res[1]))
        return render_template('Councilor/Frequent_Issues.html',val=s , val2=mine_res)
    except Exception as e:
        print(e)
        return render_template('Councilor/Frequent_Issues.html', val=[], val2=[])


@app.route('/mined_issues',methods=['post'])
def mined_issues():
    cmd.execute("SELECT`mine_result`.*,`councilor`.`first_name`,`councilor`.`second_name`FROM`councilor`INNER JOIN`mine_result`ON`mine_result`.`councilor_id`=`councilor`.`lid`")
    s=cmd.fetchall()
    print(s)
    return render_template('Councilor/mined_issue.html',val=s)


@app.route('/Councilor_Feedback')
def Councilor_Feedback():
    cmd.execute("SELECT `feedback`.*,`user`.`first_name`,`user`.`second_name`FROM`user`INNER JOIN `feedback`ON`feedback`.`lid`=`user`.`lid`")
    s=cmd.fetchall()
    return render_template('Councilor/Feedback.html',val=s)


@app.route('/Report')
def Report():
    return render_template('Councilor/Report.html')


@app.route('/submit_report',methods=['post'])
def submit_report():
    title=request.form['textfield']
    report=request.files['file']
    fn=secure_filename(report.filename)
    report.save("static/Policy/"+fn)
    cmd.execute("insert into report values(null,'"++"','"+title+"',curdate(),'"+fn+"','"+str(session['id'])+"')")
    con.commit()
    return '''<script>alert("Process Successfull")
                  window.location="/Report"
                  </script>'''


@app.route('/Suggestion')
def Suggestion():
    return render_template('Councilor/Suggestion.html')


@app.route('/send_suggestion',methods=['post'])
def send_suggestion():
    suggestion=request.form['textarea']
    cmd.execute("insert into suggestion values(null,'"+str(session['id'])+"','"+suggestion+"')")
    con.commit()
    return '''<script>alert("process successfull")
                    window.location="/Suggestion"
                    </script>'''


@app.route('/staffregistration',methods=['post'])
def staffregistration():
    cmd.execute("SELECT * FROM`department`")
    s=cmd.fetchall()
    return render_template('Department/Staff Registration.html',val=s)


@app.route('/Edit_staff')
def Edit_staff():
        id=request.args.get('id')
        session['sid']=id
        cmd.execute("SELECT * FROM`department`")
        v=cmd.fetchall()
        cmd.execute("select * from staff where id='"+str(id)+"'")
        s=cmd.fetchone()
        return render_template('Department/Edit_staff.html',val=s,det=v)


@app.route('/delete_staff')
def delete_staff():
    id=request.args.get('id')
    cmd.execute("update login set type='Deactive' where id='"+id+"'")
    con.commit()
    return '''<script>alert("Delete successfully")
              window.location="/ViewAndManageStaff"
              </script>'''


@app.route('/update_staff',methods=['post'])
def update_staff():
    lid=session['sid']
    department=request.form['select']
    first_name=request.form['textfield']
    second_name=request.form['textfield2']
    gender=request.form['RadioGroup1']
    DOB=request.form['textfield3']
    Qualification=request.form['select2']
    Phone_no=request.form['textfield4']
    Email=request.form['textfield5']
    cmd.execute("update staff set did='"+department+"',first_name='"+first_name+"',second_name='"+second_name+"',gender='"+gender+"',DOB='"+DOB+"',Qualification='"+Qualification+"',Phone_no='"+Phone_no+"',Email='"+Email+"' where id='"+str(lid)+"'")
    con.commit()
    return '''<script>alert("updated successfully")
               window.location="/ViewAndManageStaff"
               </script>'''


@app.route('/AssignWork')
def AssignWork():
    id=session['id']
    cmd.execute("SELECT`user_application`.*,`user`.`first_name`,`user`.`second_name`FROM`user`INNER JOIN`user_application`ON`user_application`.`uid`=`user`.`lid`")
    s=cmd.fetchall()
    return render_template('Department/Assign Work.html',val=s)


@app.route('/AssignForm')
def AssignForm():
        aid=request.args.get('id')
        session['aid']=aid
        id=session['id']
        cmd.execute("SELECT * FROM staff INNER JOIN login ON login.id=staff.lid WHERE staff.did='"+str(id)+"' AND login.type!='Deactive'")
        s=cmd.fetchall()
        return render_template('Department/Assign Form.html',val=s)


@app.route('/submit_work',methods=['post'])
def submit_work():
    aid=session['aid']
    clkid=request.form['select']
    work=request.form['textarea']
    completion_date=request.form['textfield']
    cmd.execute("insert into assign_work values(null,'"+str(clkid)+"','"+work+"','"+completion_date+"','pending','"+str(aid)+"')")
    con.commit()
    return '''<script>alert("Process successfull")
              window.location="/AssignWork"
              </script>'''

@app.route('/dpt_policy')
def dpt_policy():
    cmd.execute("SELECT`policy`.*,`mayor`.`first_name`,`mayor`.`second_name`FROM`mayor`INNER JOIN`policy`ON`policy`.`lid`=`mayor`.`lid`")
    s=cmd.fetchall()
    return render_template('Department/Policy.html',val=s)


@app.route('/DepartmentHome')
def DepartmentHome():
    return render_template('Department/Department Home.html')

@app.route('/Edit_department')
def Edit_department():
        id=request.args.get('id')
        session['id'] = id
        cmd.execute("select * from department where did='"+id+"'")
        s=cmd.fetchone()
        return render_template('Admin/Edit_department.html',val=s)


@app.route('/delete_department')
def delete_department():
    id=request.args.get('id')
    cmd.execute("update login set type='Deactive' where id='"+id+"'")
    con.commit()
    return '''<script>alert("Delete successfully")
              window.location="/DepartmentDetails"
              </script>'''

@app.route('/update_department',methods=['post'])
def update_department():
    lid=session['id']
    department=request.form['textfield']
    description=request.form['textarea']
    cmd.execute("update department set department='"+department+"',description='"+description+"' where did='"+lid+"'")
    con.commit()
    return '''<script>alert("update successfull")
               window.location="/DepartmentDetails"
               </script>'''



@app.route('/ManageApplication')
def ManageApplication():
    cmd.execute("SELECT `user_application`.*,`user`.`first_name`,`user`.`second_name`FROM`user`INNER JOIN `user_application`ON`user_application`.`uid`=`user`.`lid`")
    s=cmd.fetchall()
    return render_template('Department/Manage Application.html',val=s)


@app.route('/accept_apps')
def accept_apps():
    id=request.args.get('id')
    cmd.execute("update user_application set status='Accepted' where id='"+str(id)+"'")
    con.commit()
    return  '''<script>alert("Accepted Successfully")
             window.location="/ManageApplication"
             </script>'''


@app.route('/reject_apps')
def reject_apps():
    id=request.args.get('id')
    cmd.execute("update user_application set status='Rejected' where id='"+str(id)+"'")
    con.commit()
    return '''<script>alert("Rejected Successfully")
             window.location="/ManageApplication"
             </script>'''



@app.route('/ManageComplaint')
def ManageComplaint():
    cmd.execute("SELECT`complaint`.*,`councilor`.`first_name`,`councilor`.`second_name`FROM`councilor`INNER JOIN`complaint`ON`complaint`.`lid`=`councilor`.`lid`")
    s=cmd.fetchall()
    return render_template('Department/Manage Complaint.html',val=s)


@app.route('/reply')
def reply():
        id=request.args.get('id')
        session['rid']=id
        return render_template('Department/Reply Of Complaint.html')



@app.route('/post_complaint',methods=['post'])
def post_complaint():
    reply=request.form['textarea']
    complaint_id=session['rid']
    cmd.execute("update complaint set reply='"+reply+"' where id='"+complaint_id+"'")
    con.commit()
    return '''<script>alert("Process Successfull")
                  window.location="/ManageComplaint"
                  </script>'''



@app.route('/ReplyOfComplaint')
def ReplyOfComplaint():
    return render_template('Department/Reply Of Complaint.html')


@app.route('/ViewAndManageStaff')
def ViewAndManageStaff():
    cmd.execute("SELECT`staff`.*,`department`.`department`,`login`.`type`FROM`department`INNER JOIN`staff`ON`staff`.`did`=`department`.`lid`INNER JOIN`login`ON`login`.`id`=`staff`.`lid` WHERE `staff`.`did`='"+str(session['id'])+"'")
    s=cmd.fetchall()
    return render_template('Department/View And Manage Staff.html',val=s)


@app.route('/ViewRating')
def ViewRating():
    cmd.execute("SELECT`rating`.*,`user`.`first_name`,`user`.`second_name`,`department`.`department`FROM`user`INNER JOIN`rating`ON`user`.`lid`=`rating`.`lid`INNER JOIN`department`ON`department`.`did`=`rating`.`did`")
    s=cmd.fetchall()
    return render_template('Department/View Rating.html',val=s)


@app.route('/ViewReport')
def ViewReport():
    cmd.execute("SELECT`report`.*,`staff`.`first_name`,`staff`.`second_name`,`user_application`.`application` FROM`assign_work`INNER JOIN`report`ON`report`.`work_id`=`assign_work`.`id` JOIN `staff` ON `staff`.`lid`=`assign_work`.`cid` JOIN `user_application` ON `user_application`.`id`=`assign_work`.`applctid")
    s=cmd.fetchall()
    return render_template('Department/View Report.html',val=s)


@app.route('/MayorInteraction')
def MayorInteraction():
    cmd.execute("SELECT * FROM `councilor`INNER JOIN `login`ON`login`.`id`=`councilor`.`lid` WHERE`login`.`type`!='Deactive' ")
    s=cmd.fetchall()
    return render_template('Mayor/Councilor Interaction.html',val=s)


@app.route('/chat_councilor')
def chat_councilor():
        id=request.args.get('id')
        session['pid'] = id
        fromid=session['id']
        cmd.execute("SELECT `fid`,`message` FROM `chat` WHERE (`fid`='" + str(fromid) + "' AND `tid`='" + str(id) + "') OR (`fid`='" + str(id) + "' AND `tid`='"+str(fromid)+"')")
        s = cmd.fetchall()
        return render_template('Mayor/chat_councilor.html',id=str(id),data=s)



@app.route('/add_chat',methods=['post'])
def add_chat():
    msg=request.form['text']
    fromid = session['id']
    toid=session['pid']
    cmd.execute("insert into chat values(null,'"+str(fromid)+"','"+str(toid)+"','"+msg+"',curdate())")
    con.commit()



    fromid = session['id']
    id = session['pid']
    cmd.execute("SELECT `fid`,`message` FROM `chat` WHERE (`fid`='" + str(fromid) + "' AND `tid`='" + str(id) + "') OR (`fid`='" + str(id) + "' AND `tid`='" + str(fromid) + "')")
    s = cmd.fetchall()
    return render_template('Mayor/chat_councilor.html', id=str(id), data=s)


@app.route('/Feedback')
def Feedback():
    cmd.execute("SELECT `feedback`.*,`department`.`department`FROM `department`INNER JOIN `feedback`ON `feedback`.`did`=`department`.`did`")
    s = cmd.fetchall()
    return render_template('Mayor/Feedback.html',val=s)


@app.route('/MayorHome')
def MayorHome():
    return render_template('Mayor/Mayor Home.html')


@app.route('/Policies')
def Policies():
    return render_template('Mayor/Policies.html')


@app.route('/policy',methods=['post'])
def policy():
    policy_name=request.form['textfield']
    description=request.files['file']
    fn=secure_filename(description.filename)
    description.save("static/Policy/"+fn)
    cmd.execute("insert into policy values(null,'"+str(session['id'])+"','"+policy_name+"','"+fn+"')")
    con.commit()
    return '''<script>alert("Process Successfull")
              window.location="/Policies"
              </script>'''


@app.route('/Suggestions')
def Suggestions():
    cmd.execute("SELECT `suggestion`.*,`councilor`.`first_name`,`councilor`.`second_name` FROM `councilor`INNER JOIN `suggestion` ON `suggestion`.`lid`=`councilor`.`lid`")
    s=cmd.fetchall()
    return render_template('Mayor/Suggestions.html',val=s)

@app.route('/ViewIssues')
def ViewIssues():
    cmd.execute("SELECT`mine_result`.*,`councilor`.`first_name`,`councilor`.`second_name`FROM`councilor`INNER JOIN`mine_result`ON`mine_result`.`councilor_id`=`councilor`.`lid`")
    s=cmd.fetchall()
    return render_template('Mayor/View Issues.html',val=s)

@app.route('/councilor_reg',methods=['post'])
def councilor_reg():
    first_name=request.form['textfield']
    second_name=request.form['textfield2']
    gender=request.form['RadioGroup1']
    DOB=request.form['textfield3']
    Ward=request.form['textfield4']
    Phone_no=request.form['textfield5']
    Email_id=request.form['textfield6']
    Place=request.form['textfield7']
    Post=request.form['textfield8']
    Pin=request.form['textfield9']
    username=request.form['textfield11']
    password=request.form['textfield10']
    cmd.execute("insert into Login values(null,'"+username+"','"+password+"','councilor')")
    id=con.insert_id()
    cmd.execute("insert into councilor values(null,'"+str(id)+"','"+first_name+"','"+second_name+"','"+gender+"','"+DOB+"','"+Ward+"','"+Phone_no+"','"+Email_id+"','"+Place+"','"+Post+"','"+Pin+"')")
    con.commit()
    return '''<script>alert("Registration Successfull")
           window.location="/Councilordetails"
           </script>'''

@app.route('/Mayor_reg',methods=['post'])
def Mayor_reg():
    year=request.form['select']
    first_name=request.form['textfield']
    second_name=request.form['textfield2']
    gender=request.form['RadioGroup1']
    DOB=request.form['textfield3']
    Qualification=request.form['select2']
    phone_no=request.form['textfield4']
    Email=request.form['textfield5']
    username=request.form['textfield7']
    password=request.form['textfield6']
    cmd.execute("insert into Login values(null,'"+username+"','"+password+"','mayor')")
    id=con.insert_id()
    cmd.execute("insert into mayor values(null,'"+str(id)+"','"+year+"','"+first_name+"','"+second_name+"','"+gender+"','"+DOB+"','"+Qualification+"','"+phone_no+"','"+Email+"')")
    con.commit()
    return '''<script>alert("Registration Successfull")
              window.location="/MayorDetails"
              </script>'''


@app.route('/staff_reg',methods=['post'])
def staff_reg():
    department=request.form['select']
    first_name=request.form['textfield']
    second_name=request.form['textfield2']
    gender=request.form['RadioGroup1']
    DOB=request.form['textfield4']
    Qualification=request.form['select2']
    phone_no=request.form['textfield3']
    Email=request.form['textfield5']
    username=request.form['textfield7']
    password=request.form['textfield6']
    cmd.execute("insert into Login values(null,'" +username+ "','" + password + "','staff')")
    id = con.insert_id()
    cmd.execute("insert into staff values(null,'"+str(id)+"','"+department+"','" +first_name+ "','" +second_name+ "','"+gender+"','" +DOB+ "','" + Qualification + "','" + phone_no + "','" +Email+ "')")
    con.commit()
    return '''<script>alert("Registration Successfull")
                 window.location="/ViewAndManageStaff"
                 </script>'''


@app.route('/department_reg',methods=['post'])
def department_reg():
    department_name=request.form['textfield']
    description=request.form['textarea']
    username=request.form['textfield2']
    password=request.form['textfield3']
    cmd.execute("insert into Login values(null,'" + username + "','" + password + "','department')")
    id = con.insert_id()
    cmd.execute("insert into department values(null,'"+str(id)+"','"+department_name+"','"+description+"')")
    con.commit()
    return '''<script>alert("Registration Successfull")
                    window.location="/DepartmentDetails"
                    </script>'''

@app.route('/logout')
def logout():
    session.clear()
    return render_template('/login.html')

@app.route('/log',methods=['POST'])
def log():
    username=request.form['textfield']
    password=request.form['textfield2']
    cmd.execute("select * from login where username='"+username+"' and password='"+password+"'")
    s=cmd.fetchone()


    if s is None :
        return '''<script>alert("Invalid username and password")
        window.location="/"
        </script>'''


    elif s[3]=="admin":
        session['id'] = s[0]
        return '''<script>
               window.location="/MayorDetails"
               </script>'''


    elif s[3]=="staff":
        session['id'] = s[0]
        return '''<script>
               window.location="/ViewApplication"
               </script>'''


    elif s[3] == "department":
        session['id']=s[0]
        return '''<script>
               window.location="/ManageApplication"
               </script>'''


    elif s[3] == "councilor":
        session['id']=s[0]
        return '''<script>
               window.location="/separate_chat"
               </script>'''

    elif s[3] == "mayor":
        session['id']=s[0]
        return '''<script>
                  window.location="/ViewIssues"
                  </script>'''

    else :
        return '''<script>alert("Invalid username and password")
        window.location="/"
        </script>'''


if __name__ =='__main__':
    app.run(debug=True)