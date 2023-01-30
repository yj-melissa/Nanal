import ProfileForm from "./profile/ProfileForm";
import Settings from "./Settings.js";

function MyPage() {
  return (
    <div>
      <ProfileForm />
      <hr className="border-solid border-1 border-slate-800 w-80 my-5" />
      <Settings />
    </div>
  );
}

export default MyPage;
