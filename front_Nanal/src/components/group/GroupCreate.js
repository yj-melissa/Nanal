import GroupProfile from './GroupProfile';

function GroupCreate() {
  return (
    <div>
      <h2> 그룹 생성 </h2>
      <GroupProfile />
      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />
    </div>
  );
}

export default GroupCreate;
