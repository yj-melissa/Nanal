import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

const getStringDate = (date) => {
  return date.toISOString().slice(0, 10);
};

function DiaryCreate() {
  // 날짜, 일기, 그룹여부 데이터 받기
  const [date, setDate] = useState(getStringDate(new Date()));
  const [content, setContent] = useState('');
  const [group, setGroup] = useState('private');

  // 포커싱 기능
  const contentRef = useRef();

  // 작성완료 버튼 누르면 실행되는 함수 - axios 사용해서 백s엔드와 통신
  const handleSubmit = (e) => {
    e.preventDefault();
    // 유효성 검사 후 포커싱
    if (content.length < 2) {
      contentRef.current.focus();
      return;
    }
    onLogin();
    axios_api
      .post('diary', {
        // 날짜 데이터도 전달하기
        // 선택한 그룹은 배열 형태로 전달해야 함
        groupIdxList: checkedList,
        content: content,
      })
      .then((response) => {
        alert('저장 성공');
        // 일기 생성 후 홈으로 보내기
        navigate('/', { replace: true });
      })
      .catch((err) => {
        console.log(err);
      });

    // 저장 후 일기 데이터 초기화
    setContent('');
    setGroup('private');
  };

  // 체크된 그룹을 넣어줄 배열
  const [checkedList, setCheckedList] = useState([]);
  // input 태그가 체크된 경우 실행되는 함수
  const onChecked = (checked, id) => {
    if (checked) {
      setCheckedList([...checkedList, id]);
    } else {
      setCheckedList(checkedList.filter((el) => el !== id));
    }
  };

  // 뒤로가기 기능
  const navigate = useNavigate();
  // 그룹 리스트 데이터 가져오기
  const [groupList, setGroupList] = useState([]);
  useEffect(() => {
    onLogin();
    axios_api
      .get('group/list')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupList(null);
          if (data.data.responseMessage === '그룹 리스트 조회 성공') {
            setGroupList(data.data.groupList);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 리스트 불러오기 오류: ' + error);
      });
  }, []);

  // 그룹 리스트 보여줄지 말지
  const [isShow, setShow] = useState(false);

  return (
    <div className='border-solid border-1 border-black text-center p-10'>
      <h2>일기 작성하기</h2>
      {/* 날짜 선택란 */}
      <div>
        <input
          value={date}
          onChange={(e) => setDate(e.target.value)}
          type='date'
        />
      </div>
      {/* 일기 내용 작성란 */}
      <div>
        <textarea
          className='mb-10 w-full h-min'
          name='content'
          ref={contentRef}
          value={content}
          onChange={(e) => {
            setContent(e.target.value);
          }}
        />
      </div>
      {/* 그룹 여부 선택란 */}
      <div>
        <input
          type='radio'
          value='개인'
          checked={group === '개인'}
          onChange={(e) => setGroup(e.target.value)}
          onClick={() => setShow(false)}
        />
        <label>개인</label>
        <input
          type='radio'
          value='그룹'
          checked={group === '그룹'}
          onChange={(e) => setGroup(e.target.value)}
          onClick={() => setShow(true)}
        />
        <label>그룹</label>
        {isShow ? (
          <>
            {groupList.map((groupItem, idx) => {
              return (
                <div
                  key={idx}
                  className='bg-[#F7F7F7] border-2 border-solid border-slate-400 rounded-lg m-1 mb-3 p-2'
                >
                  <label htmlFor={groupItem.groupDetail.groupIdx}>
                    {groupItem.groupDetail.groupName}
                  </label>
                  <input
                    type='checkbox'
                    id={groupItem.groupDetail.groupIdx}
                    checked={
                      checkedList.includes(groupItem.groupDetail.groupIdx)
                        ? true
                        : false
                    }
                    onChange={(e) => {
                      onChecked(
                        e.target.checked,
                        groupItem.groupDetail.groupIdx
                      );
                    }}
                  />
                </div>
              );
            })}
          </>
        ) : (
          <></>
        )}
      </div>
      {/* 작성 취소 및 완료 버튼 */}
      <div className='flex justify-between'>
        <button onClick={() => navigate(-1)}>작성 취소</button>
        <button onClick={handleSubmit}>작성 완료</button>
      </div>
    </div>
  );
}

export default DiaryCreate;
