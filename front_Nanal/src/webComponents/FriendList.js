import React, { useState } from 'react';
import nmy from '../src_assets/img/bookmark-name/name-mark-yellow.svg';
import diaryImgYellow from '../src_assets/img/diary-img-yellow.svg';

const FriendList = () => {
  return (
    <div className='relative w-[1440px] mx-auto'>
      <p className='absolute z-30 left-[330px] inset-y-28'>친구 목록</p>
      <img src={nmy} className='absolute z-20 left-60 inset-y-20' />
      <img src={diaryImgYellow} className='absolute w-[1440px] z-10' />
      <div></div>
    </div>
  );
};

export default FriendList;
