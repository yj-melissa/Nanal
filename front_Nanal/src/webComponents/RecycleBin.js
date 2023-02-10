import nmg from '../src_assets/img/bookmark-name/name-mark-green.svg'
import diaryImgGreen from '../src_assets/img/diary-img-green.svg'
import React, { useState } from 'react';

const RecycleBin = () => {
  return <div className="relative w-[1440px] mx-auto">

    <p className="absolute z-30 left-[330px] inset-y-28">휴지통</p>
    <img src={nmg} className='absolute z-20 left-60 inset-y-20'/>
    <img src={diaryImgGreen} className='absolute w-[1440px] z-10' />
    <div>
      
    </div>
  </div>
}

export default RecycleBin