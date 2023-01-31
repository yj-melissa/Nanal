import ProfileForm from './profile/ProfileForm';
import Settings from './Settings.js';

function MyPage() {
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
