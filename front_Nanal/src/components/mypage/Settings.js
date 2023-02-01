import { Link } from 'react-router-dom';
import bookmarksRed from '../../src_assets/img/bookmarksRed.png';
import groupYellow from '../../src_assets/img/groupYellow.png';
import recycleBinImg from '../../src_assets/img/recycleBin.png';
import logOutImg from '../../src_assets/img/log-out.svg';
import settingImg from '../../src_assets/img/cog.svg';

function Settings() {
  return (
    <div>
      <div className='grid grid-cols-3 gap-6'>
        <Link to='/Group/List' className='grid content-evenly'>
          <img src={bookmarksRed} className='m-auto' />
          <p className='text-center my-1'>그룹 관리</p>
        </Link>
        <Link to='/Friend/List' className='grid content-evenly'>
          <img src={groupYellow} className='m-auto' />
          <p className='text-center my-1'>친구 목록</p>
        </Link>
        <Link to='/RecycleBin' className='grid content-evenly'>
          <img src={recycleBinImg} className='m-auto' />
          <p className='text-center my-1'>휴지통</p>
        </Link>
      </div>
      <br />
      <br />
      <div className='grid grid-cols-2'>
        <Link to='/Group/List' className='grid content-evenly pl-4'>
          <img src={logOutImg} className='w-10 h-10 m-auto' />
          <p className='text-center my-1 ml-3'>로그아웃</p>
        </Link>
        <Link to='/Friend/List' className='grid content-evenly pr-10'>
          <img src={settingImg} className='w-10 h-10 m-auto' />
          <p className='text-center my-1'>설정</p>
        </Link>
      </div>
    </div>
  );
}

export default Settings;
