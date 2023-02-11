import React, { useState } from 'react';
import nmy from '../src_assets/img/bookmark-name/name-mark-yellow.svg';
import diaryImgYellow from '../src_assets/img/diary-img-yellow.svg';
import FList from './friend/FriendList'
import FriendAdd from './friend/FriendAdd'
import FriendDetail from './friend/FriendDetail'

const FriendList = () => {
  const [friendCompo, setFriendCompo] = useState([true, false, false])
  const [userIdx, setUserIdx] = useState()

  return (
    <div className='relative w-[1440px] mx-auto'>
      <p className='absolute z-30 left-[330px] inset-y-28'>친구 목록</p>
      <img src={nmy} className='absolute z-20 left-60 inset-y-20' />
      <img src={diaryImgYellow} className='absolute w-[1280px] z-10 left-12' />
      <div className="absolute z-20 left-52 inset-y-[184px]">
        <FList setFriendAdd={setFriendCompo} setUserIdx={setUserIdx} />
      </div>
      {friendCompo[0] === true ? null :
      friendCompo[1] === true ?
      <div className="absolute z-20 right-80 inset-y-[72px]">
        <FriendAdd />
      </div> :
      friendCompo[2] === true ?
      <div className="absolute z-20 right-80 inset-y-44">
        <FriendDetail userIdx={userIdx} />
      </div> : null
      }
    </div>
  );
};

export default FriendList;
