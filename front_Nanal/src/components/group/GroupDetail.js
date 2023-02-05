import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import settingIcon from '../../src_assets/img/setting_icon.png';

function GroupDetail() {
  const { state } = useLocation();

  const [groupDetail, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`/group/${state.groupIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupName(null);
          setGroupTag(null);
          if (data.data.responseMessage === '그룹 조회 성공') {
            setGroupName(data.data.groupDetail);
            setGroupTag(data.data.tags);
            const groupidx = data.data.groupDetail.groupIdx;
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
    <div>
      <h1>그룹 상세 페이지</h1>{' '}
      <Link
        to={`/Group/Setting/${groupDetail.groupIdx}`}
        state={{ groupIdx: groupDetail.groupIdx }}
        className='inline-block float-right'
      >
        <img src={settingIcon} className='w-[20px] h-[20px] mx-1.5' />
      </Link>
      <div>
        <p>{groupDetail.groupName}</p>

        {groupTag.map((tagging, idx) => {
          return <span key={idx}>#{tagging.tag}&nbsp;</span>;
        })}
      </div>
      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />
    </div>
  );
}

export default GroupDetail;
