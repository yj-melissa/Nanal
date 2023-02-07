import React from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import axios_api from '../../config/Axios';

// 친구 등록 = friend
// 그룹 초대 = group/join
// 새 글 알림 = 해당 글로 이동
// 새 댓글 알림 = 해달 글로 이동

function AlarmItem({
  content,
  noticeType,
  requestDiaryIdx,
  requestGroupIdx,
  requestUserIdx,
  noticeIdx,
}) {
  // 새 글이나 댓글은 해당 글로 이동 시켜줘야함.
  let navigate = useNavigate();

  // 친구 등록 성공.
  const addFriend = () => {
    axios_api
      .post('friend', {
        friendIdx: requestUserIdx,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '친구 등록 성공') {
            checkAlarm();
            Swal.fire({
              icon: 'success', // Alert 타입
              text: '친구 등록에 수락했어요!', // Alert 내용
              width: '35%',
            }).then(function () {
              window.location.reload(true);
            });
            // navigate(`/Friend/List`);
          } else if (data.data.responseMessage === '이미 친구로 등록됨') {
            checkAlarm();
            Swal.fire({
              icon: 'info', // Alert 타입
              text: '이미 친구에요!', // Alert 내용
              width: '30%',
            }).then(function () {
              window.location.reload(true);
            });
          }
        } else {
          console.log('친구 등록 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('친구 등록 오류: ' + error);
      });
  };

  //그룹 초대 성공.
  const addGroup = () => {
    axios_api
      .post('group/join', {
        groupIdx: requestGroupIdx,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '그룹 가입 성공') {
            checkAlarm();
            Swal.fire({
              icon: 'success', // Alert 타입
              text: '그룹 가입에 수락했어요!', // Alert 내용
              width: '35%',
            }).then(function () {
              window.location.reload(true);
            });
            // navigate(`/Group/List`);
          } else if (data.data.responseMessage === '이미 친구로 등록됨') {
            checkAlarm();
            Swal.fire({
              icon: 'info', // Alert 타입
              text: '이미 처리된 사항이에요!', // Alert 내용
              width: '35%',
            }).then(function () {
              //window.location.reload(true);
            });
          }
        } else {
          console.log('그룹 가입 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 가입 오류: ' + error);
      });
  };

  //알람 읽음 처리
  const checkAlarm = () => {
    axios_api
      .get(`notification/${noticeIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '알림 읽음 처리 성공') {
            Location.reload();
          }
        } else {
          console.log('알림 읽음 처리 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('알림 읽음 처리 오류: ' + error);
      });
  };

  //알람 읽음 처리 후 해당 글로.
  const linkedDiary = () => {
    axios_api
      .get(`notification/${noticeIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '알림 읽음 처리 성공') {
            navigate(`diary/${requestDiaryIdx}`);
          }
        } else {
          console.log('알림 읽음 처리 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('알림 읽음 처리 오류: ' + error);
      });
  };

  if (noticeType === 0) {
    return (
      <div className='mb-3'>
        <hr className='my-1 border-dashed border-stone-800' />
        <div className='flex justify-between'>
          <div className='flex justify-between my-1'>
            <p className='text-base'>{content}</p>
            {/* <button className='grid content-start'>X</button> */}
          </div>
          <div className='flex justify-end'>
            <button
              onClick={() => {
                addFriend();
                // checkAlarm();
              }}
              className='bg-cyan-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
            >
              O
            </button>
            <button
              onClick={checkAlarm}
              className='bg-rose-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
            >
              X
            </button>
          </div>
        </div>
      </div>
    );
  } else if (noticeType === 1) {
    return (
      <div className='my-3'>
        <hr className='my-2 border-dashed border-stone-800' />
        <div className='flex justify-between'>
          <p>{content}</p>
          {/* <button className='grid content-start'>X</button> */}
        </div>
        <div className='flex justify-end'>
          <button
            onClick={() => {
              addGroup();
              // checkAlarm();
            }}
            className='bg-cyan-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
          >
            수락
          </button>
          <button
            onClick={checkAlarm}
            className='bg-rose-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
          >
            거절
          </button>
        </div>
      </div>
    );
  } else if (noticeType === 2) {
    return (
      <div className='my-3'>
        <hr className='my-2 border-dashed border-stone-800' />
        <div className='flex justify-between'>
          <p>{content}</p>
        </div>
        <div className='flex justify-end'>
          <button
            onClick={checkAlarm}
            className='bg-teal-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
          >
            확인
          </button>
          <button
            onClick={linkedDiary}
            className='bg-teal-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
          >
            바로가기
          </button>
        </div>
      </div>
    );
  } else if (noticeType === 3) {
    return (
      <div className='my-3'>
        <hr className='my-2 border-dashed border-stone-800' />
        <div className='flex justify-between'>
          <p>{content}</p>
          {/* <button className='grid content-start'>X</button> */}
        </div>
        <div className='flex justify-end'>
          <button
            onClick={checkAlarm}
            className='bg-teal-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
          >
            확인
          </button>
          <button
            onClick={linkedDiary}
            className='bg-teal-500 text-white px-2.5 py-1 rounded-3xl m-auto mx-3 inline-block'
          >
            바로가기
          </button>
        </div>
      </div>
    );
  } else {
    return <></>;
  }
}

export default AlarmItem;
