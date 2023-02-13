import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import DiaryList from '../diary/DiaryList';
import settingIcon from '../../src_assets/img/setting_icon.png';

function GroupDetail({groupIdx, setGroupCompo}) {

  const [groupDetail, setGroupDetail] = useState('');
  const [groupTag, setGroupTag] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`/group/${groupIdx}`)
      .then(({ data }) => {
        console.log(data)
        if (data.statusCode === 200) {
          setGroupDetail(null);
          setGroupTag(null);
          if (data.data.responseMessage === '그룹 조회 성공') {
            setGroupDetail(data.data.groupDetail);
            setGroupTag(data.data.tags);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 상세 보기 오류: ' + error);
      });
  }, []);

  return (
    <div className='text-center'>
      <div
        onClick={setGroupCompo([false, false, false, true, false])}
      >
        <img src={settingIcon} className='w-[20px] h-[20px] mx-1.5' />
      </div>
      <div>
        <p className='mb-1 text-2xl font-bold text-center'>
          {groupDetail.groupName}
        </p>
        {groupTag.map((tagging, idx) => {
          return (
            <span
              key={idx}
              className='inline-block p-1 mx-1 my-1 text-xs break-all rounded-lg bg-stone-200 hover:bg-blue-200'
            >
              #{tagging.tag}&nbsp;
            </span>
          );
        })}
      </div>
      {/* <hr className='mx-5 my-5 text-center border-solid w-72 border-1 border-slate-600' /> */}

      {/* 일기 리스트 */}
      <DiaryList groupIdx={groupIdx} />
    </div>
  );
}

export default GroupDetail;
