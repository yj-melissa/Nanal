import { useEffect } from 'react';
import ProfileForm from './profile/ProfileForm';
import Settings from './profile/Settings.js';
import { onLogin } from '../../config/Login';

function MyPage() {
  useEffect(() => {
    onLogin();
  }, []);

  return (
    <div>
      <ProfileForm />
      <hr
        className='border-slate-500/75
       w-80 my-5'
      />
      <Settings />
    </div>
  );
}

export default MyPage;
