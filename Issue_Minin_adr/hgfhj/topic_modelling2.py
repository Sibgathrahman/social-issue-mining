import MySQLdb
con=MySQLdb.connect(host='localhost',user='root',password='',port=3306,db='issue_mining')
cmd=con.cursor()


# print(s)

from sklearn.feature_extraction.text import TfidfVectorizer, CountVectorizer
from sklearn.datasets import fetch_20newsgroups
from sklearn.decomposition import NMF, LatentDirichletAllocation

class Topic_modellingg():

    def Topic_modell(self,s):
        # print("s---------",s)
        result_documents = {}


        def display_topics(model, feature_names, no_top_words):
            topwords=[]

            for topic_idx, topic in enumerate(model.components_):
                # print ("Topic is %d:" % (topic_idx))
                # print( " ".join([feature_names[i]for i in topic.argsort()[:-no_top_words - 1:-1]]))
                result=( " ".join([feature_names[i]for i in topic.argsort()[:-no_top_words - 1:-1]]))
                # print(topic_idx,result)
                topwords.append(result)

            for doc in documents:
             cnt=0
             for topics in topwords:
                if all(word in doc for word in  topics.split(" ") ):
                    tindex=topwords.index(topics)
                    if tindex in result_documents:
                        result_documents[tindex]+=doc
                    else:
                        result_documents[tindex]=doc
            # print("result---",result_documents)




        # documents = [doc1,doc2,doc3,doc4,doc5]

        documents=[]
        for i in s:
            # print(i[0])
            documents.append(i[4])
        # print("doc---------",documents)


        no_features = 1000


        # LDA can only use raw term counts for LDA because it is a probabilistic graphical model

        tf_vectorizer = CountVectorizer(max_df=0.95, min_df=2, max_features=no_features, stop_words='english')
        tf = tf_vectorizer.fit_transform(documents)
        tf_feature_names = tf_vectorizer.get_feature_names()

        no_topics = 2


        # Run LDA
        lda = LatentDirichletAllocation (n_components=no_topics, max_iter=5, learning_method='online', learning_offset=90.,random_state=0).fit(tf)

        no_top_words = 2

        display_topics(lda, tf_feature_names, no_top_words)

        return result_documents