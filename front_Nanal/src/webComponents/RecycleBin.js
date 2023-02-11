import nmb from '../src_assets/img/bookmark-name/name-mark-blue.svg'
import diaryImgBlue from '../src_assets/img/diary-img-blue.svg'
import React, { useState } from 'react';

const RecycleBin = () => {
  return <div className="relative w-[1440px] mx-auto">

    <p className="absolute z-30 left-[330px] inset-y-28">휴지통</p>
    <img src={nmb} className='absolute z-20 left-60 inset-y-20'/>
    <img src={diaryImgBlue} className='absolute w-[1280px] z-10 left-12' />
    
    <div>
      
    </div>
  </div>
}

export default RecycleBin