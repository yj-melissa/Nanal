import nmg from '../src_assets/img/bookmark-name/name-mark-green.svg'
import diaryImgGreen from '../src_assets/img/diary-img-green.svg'
import React, { useState } from 'react';
import Tuning from './setting/Tuning'

const SettingsNanal = () => {
  return <div className="relative w-[1440px] mx-auto">

    <p className="absolute z-30 left-[330px] inset-y-28">설정</p>
    <img src={nmg} className='absolute z-20 left-60 inset-y-20'/>
    <img src={diaryImgGreen} className='absolute w-[1280px] z-10 left-12' />
    <div className="absolute z-20 left-[240px] inset-y-48">
      <Tuning />
    </div>
  </div>
}

export default SettingsNanal