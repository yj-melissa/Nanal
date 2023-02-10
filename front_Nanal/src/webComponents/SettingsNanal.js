import nmb from '../src_assets/img/bookmark-name/name-mark-blue.svg'
import diaryImgBlue from '../src_assets/img/diary-img-blue.svg'
import React, { useState } from 'react';

const SettingsNanal = () => {
  return <div className="relative w-[1440px] mx-auto">

    <p className="absolute z-30 left-[330px] inset-y-28">설정</p>
    <img src={nmb} className='absolute z-20 left-60 inset-y-20'/>
    <img src={diaryImgBlue} className='absolute w-[1440px] z-10' />
    <div>
      
    </div>
  </div>
}

export default SettingsNanal