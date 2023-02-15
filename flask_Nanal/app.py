from flask import Flask, request
import nltk
from nltk.stem import WordNetLemmatizer
from nltk.tag import pos_tag
from nltk.corpus import stopwords
from nltk import sent_tokenize
from nltk import word_tokenize
nltk.download('punkt')
nltk.download('averaged_perceptron_tagger')
nltk.download('stopwords')
nltk.download('wordnet')
import pandas as pd
import flask
import openai
import joblib
import re
from konlpy.tag import Okt
from sklearn.feature_extraction.text import CountVectorizer
from collections import Counter

from nltk.stem import WordNetLemmatizer
from nltk.tag import pos_tag
from nltk.corpus import stopwords
from nltk import sent_tokenize
from nltk import word_tokenize

nltk.download('punkt')
nltk.download('averaged_perceptron_tagger')
nltk.download('stopwords')
nltk.download('wordnet')
# import os

app = flask.Flask(__name__)

@app.route("/predict", methods=['POST'])
def predict():
    print("predict")
    sentence = request.get_json()['content']

    print("sentence : "+sentence)
    model = joblib.load('model_logi.pb')
    df3 = pd.read_csv('vect_data.csv', encoding='cp949')
    x_feature = df3["sentence"]
    vect = CountVectorizer(min_df=5).fit(x_feature)

    words = []
    # 환경변수 설정 로컬
    # os.environ['JAVA_HOME'] = 'C:\Program Files\Zulu\zulu-8'

    okt = Okt()

    stop_words = [
        "나", "내", "과", "무엇", "거", "있다", "요즘", "다", "날", "되다", "있다", "노", "오늘", "김", "그래서", "우리", "저희", "따라", "의해", "에게",
        "뿐", "의거", "하여", "근거", "입각", "기준", "예", "하면", "들면", "들자면", "저", "소인", "소생", "저희", "다른", "물론", "또한", "그리고", "비길", "수",
        "없다", "해서는", "막론", "비록", "더라도", "하다", "만", "하는", "편이", "불문", "향", "하여", "쪽", "틈타", "어떻다", "달", "타다", "오르다", "것",
        "얼마", "제외", "이", "외", "밖", "하여야", "한다면", "이", "곳", "여기", "부터", "기점", "따라서", "할", "생각", "하려고", "하다", "함", "때", "앞", "중",
        "보는데서", "해야", "일것이다", "할줄", "알다", "등", "등등", "제", "겨우", "단지",  "다만", "할", "딩동", "대해", "대하", "얼마나", "얼마만큼", "큼", "얼",
        "남짓", "남자", "여자", "남", "여", "녀", "얼마간", "약간", "다소", "좀", "조금", "다수", "몇", "하물며", "또한", "이외", "대해", "말", "하자면", "다음",
        "이", "저", "그", "바꾸어서", "말", "만약", "툭", "각", "각각", "여러분", "각종", "각자", "제각기", "하도록", "까닭", "관", "하여", "진짜", "무슨", "어디",
        "곳", "더군다나", "언제", "또", "혹시", "젠", "되어", "및", "이번", "다음", "된", "거의", "이제", "감", "점점", "사람", "같다", "전", "후", "데", "매일", "받다",
        "난", "왜",  "왼쪽", "오른쪽", "오늘날", "친구", "두", "해", "나다"]

    for word in okt.pos(sentence, stem=True):
      if word[1] in ['Noun', 'Verb', 'Adjective']:
        word = re.sub('[-=+,#/\?:^$.@*\"※~&%ㆍ!』\\‘|\(\)\[\]\<\>`\'…》]', '', word[0])
        if word != '':
          if word not in stop_words:
            words.append(word)
      sentence = ' '.join(words)

    print("정제 결과 : " + sentence)

    vector = vect.transform([sentence])

    result = model.predict(vector)
    response = {"result": result[0]}
    return flask.jsonify(response)


@app.route("/dalle", methods=['POST'])
def make_picture():
    print("dalle")
    # pixel 옵션 설정 + request 데이터 받기
    content = "pixel art, pastel, lightly" + request.get_json()['content']

    openai.api_key = request.get_json()['key']
    response = openai.Image.create(
      prompt=content,
      n=1,
      size="256x256"
    )
    print(response)
    return response


@app.route("/keySentence", methods=['POST'])
def key_sentence():
    get_value = request.get_json()
    value = get_value['content']

    # 전처리
    en_arr = word_tokenize(value)

    # 원형 복원
    lm = WordNetLemmatizer()
    result = [lm.lemmatize(word, pos='v') for word in en_arr]
    result = [lm.lemmatize(word, pos='n') for word in result]

    # 품사 나누기
    tag_arr = pos_tag(result)
    tag = ['NN', 'JJ', 'JJR', 'JJS', 'NN', 'NNS', 'VB', 'VBD', 'VBG', 'VBN', 'VBP', 'VBZ']
    result = []
    for word in tag_arr:
        if word[1] in tag:
            result.append(word[0])

    # 불용어 처리
    stop_words = stopwords.words('english')

    result_arr = []
    for word in result:
        if word not in stop_words:
            result_arr.append(word)

    # 빈도수
    count = Counter(result_arr)
    count = count.most_common()

    result_arr = []

    # for i in range(0, len(count) // 2):
    #     result_arr.append(count[i])
    for i in range(0, len(count)):
        if (count[i][1] > 1):
            result_arr.append(count[i])

    # 핵심 문장 추출
    sentence = sent_tokenize(value)  # 문장 단위로 자르기

    temp = []
    # for s in sentence:
    #     cnt = 0
    #     for c in result_arr:
    #         if c[0] in s:
    #             cnt += 1
    #     temp.append(cnt)
    for s in sentence:
        cnt = 0
        arr = word_tokenize(s)
        arr = [lm.lemmatize(word, pos='v') for word in arr]
        arr = [lm.lemmatize(word, pos='n') for word in arr]
        for c in result_arr:
            if c[0] in arr:
                cnt += 1
        temp.append(cnt)

    # 빈도수가 높은 단어가 포함된 문장 뽑기
    # max_value = max(temp)
    sorted_arr = sorted(temp, reverse=True)

    str_temp = 0
    idx_temp = []

    for i in range(len(temp)):
        for j in range(len(temp)):
            if (temp[j] == sorted_arr[i]):
                if (str_temp + len(sentence[j]) < 260):
                    idx_temp.append(j)
                    str_temp += len(sentence[j])
                else:
                    break
    idx_temp.sort()

    str = ""
    for i in range(len(idx_temp)):
        str += sentence[idx_temp[i]]

    # for i in range(len(temp)):
    #     if (temp[i] == max_value):
    #         str += sentence[i]

    return str

