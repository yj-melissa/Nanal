import nmo from '../src_assets/img/bookmark-name/name-mark-orange.svg'
import diaryImgOrange from '../src_assets/img/diary-img-orange.svg'
import React, { useState } from 'react';

const GroupDiary = () => {
  return <div className="relative w-[1440px] mx-auto">

    <p className="absolute z-30 left-[330px] inset-y-28">그룹일기</p>
    <img src={nmo} className='absolute z-20 left-60 inset-y-20'/>
    <img src={diaryImgOrange} className='absolute w-[1280px] z-10 left-12' />
    <div>
      
    </div>
  </div>
}

export default GroupDiary