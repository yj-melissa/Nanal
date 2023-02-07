import { set } from 'date-fns';
import { useState, useEffect, useRef } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function DiaryUpdate() {
  const location = useLocation();
  const [group, setGroup] = useState('개인');
  const navigate = useNavigate();

  // 수정된 날짜, 일기, 기존 그룹 리스트 데이터
  const [localDate, setLocalDate] = useState(
    location.state.diaryDetail.diaryDate
  );
  const [localContent, setLocalContent] = useState(
    location.state.diaryDetail.content
  );
  const localConetRef = useRef();

  // 체크된 그룹을 넣어줄 배열
  const originGroupList = location.state.originGroupList;
  const [checkedList, setCheckedList] = useState(originGroupList);

  // 수정 취소 버튼 클릭 시 실행되는 함수
  const handleQuitEdit = () => {
    setLocalDate(location.state.diaryDate);
    setLocalContent(location.state.diaryDetail.content);
    navigate(-1);
  };

  // input 태그가 체크된 경우 실행되는 함수 = 다중 선택 가능
  const onChecked = (checked, id) => {
    if (checked) {
      setCheckedList([...checkedList, id]);
    } else {
      setCheckedList(checkedList.filter((el) => el !== id));
    }
  };

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
            if (checkedList.length !== 0) {
              setGroup('그룹');
              setShow(true);
            }
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
    <div>
      <div className='h-auto min-h-full pb-5'>
        <h2 className='my-5 text-lg font-bold text-center'>일기 수정하기</h2>
        {/* 날짜 선택란 */}
        <div>
          <input
            className='p-2 rounded-lg cursor-pointer bg-slate-300/50'
            value={localDate}
            onChange={(e) => setLocalDate(e.target.value)}
            type='date'
          />
          {/* 일기 내용 작성란 */}
          <div>
            <textarea
              className='w-full h-auto px-2 py-2 my-2 rounded-lg bg-slate-300/50'
              name='content'
              ref={localConetRef}
              value={localContent}
              onChange={(e) => {
                setLocalContent(e.target.value);
              }}
            />
          </div>
        </div>
        {/* 그룹 여부 선택란 */}
        <div className='mt-2'>
          <h4 className='my-2 text-lg font-bold'>공개 범위 설정</h4>
          <input
            className='cursor-pointer'
            id='private'
            type='radio'
            value='개인'
            checked={group === '개인'}
            onChange={(e) => setGroup(e.target.value)}
            onClick={() => {
              setShow(false);
              setCheckedList([]);
            }}
          />
          <label className='ml-2 mr-4 cursor-pointer' htmlFor='private'>
            개인
          </label>
          <input
            className='cursor-pointer'
            id='group'
            type='radio'
            value='그룹'
            checked={group === '그룹'}
            onChange={(e) => setGroup(e.target.value)}
            onClick={() => setShow(true)}
          />
          <label className='ml-2 cursor-pointer' htmlFor='group'>
            그룹
          </label>
          {isShow ? (
            <>
              {groupList.map((groupItem, idx) => {
                return (
                  <div
                    key={idx}
                    className='bg-[#F7F7F7] border-2 border-solid border-slate-400 rounded-lg m-1 mb-3 p-2'
                  >
                    <label
                      htmlFor={groupItem.groupDetail.groupIdx}
                      className='cursor-pointer'
                    >
                      {groupItem.groupDetail.groupName}
                    </label>
                    <input
                      className='cursor-pointer'
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
        {/* 수정 취소 및 수정 완료 버튼 */}
        <footer className='relative flex justify-between px-1 pb-5 translate-y-full'>
          <button
            className='hover:bg-slate-300 bg-slate-300/50 rounded-xl px-2.5 py-1 block'
            onClick={handleQuitEdit}
          >
            수정 취소
          </button>
          <button
            className='hover:bg-sky-700 bg-cyan-600 text-white px-2.5 py-1 rounded-xl block'
            onClick={() => {
              axios_api
                .put('diary', {
                  userIdx: location.state.diaryDetail.userIdx,
                  diaryIdx: location.state.diaryDetail.diaryIdx,
                  content: localContent,
                  diaryDate: localDate,
                  groupIdxList: checkedList,
                })
                .then(({ data }) => {
                  navigate('/Diary/Detail', {
                    state: {
                      diaryIdx: location.state.diaryDetail.diaryIdx,
                    },
                    replace: true,
                  });
                })
                .catch((err) => console.log(err));
            }}
          >
            수정 완료
          </button>
        </footer>
      </div>
    </div>
  );
}

export default DiaryUpdate;
