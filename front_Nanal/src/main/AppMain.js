import { Routes, Route } from 'react-router-dom';
import LogoHome from '../components/LogoHome.js';
import Calendar from '../components/Calendaar.js';
import BookCase from '../components/BookCase.js';
import SignIn from '../components/account/SignIn.js';
import SignUp from '../components/account/SignUp.js';
import DiaryCreate from '../components/diary/DiaryCreate';
import DiaryList from '../components/diary/DiaryList';
import DiaryDetail from '../components/diary/DiaryDetail';
import DiaryTotalList from '../components/diary/DiaryTotalList';
import BookmarkList from '../components/diary/BookmarkList';
import GroupCreate from '../components/group/GroupCreate';
import GroupList from '../components/group/GroupList';
import GroupDetail from '../components/group/GroupDetail';
import GroupSetting from '../components/group/GroupSetting';
import GroupUpdate from '../components/group/GroupUpdate';
import FriendList from '../components/friend/FriendList';
import FriendDetail from '../components/friend/FriendDetail';
import FriendAdd from '../components/friend/FriendAdd';
import AlarmList from '../components/another/AlarmList.js';
// import AlarmItem from '../components/another/AlarmItem.js';
import MyPage from '../components/mypage/MyPage.js';
import RecycleBin from '../components/another/RecycleBin';
import NotFound from '../components/another/NotFound.js';

const AppMain = ({ isCalendaar }) => {
  return (
    <Routes>
      <Route path='/' element={<LogoHome />}></Route>
      {isCalendaar ? (
        <Route path='/home' element={<Calendar />}></Route>
      ) : (
        <Route path='/home' element={<BookCase />}></Route>
      )}
      <Route path='/SignUp' element={<SignUp />}></Route>
      <Route path='/SignIn' element={<SignIn />}></Route>
      <Route path='/Diary/Create' element={<DiaryCreate />}></Route>
      <Route path='/Diary/List' element={<DiaryList />}></Route>
      <Route path='/Diary/Total/List' element={<DiaryTotalList />}></Route>
      <Route path='/Diary/Bookmark/List' element={<BookmarkList />}></Route>
      <Route path='/Diary/:diaryIdx' element={<DiaryDetail />}></Route>
      <Route path='/Group/Create' element={<GroupCreate />}></Route>
      <Route path='/Group/List' element={<GroupList />}></Route>
      <Route path='/Group/:groupIdx' element={<GroupDetail />}></Route>
      <Route path='/Group/Setting/:groupIdx' element={<GroupSetting />}></Route>
      <Route path='/Group/Update/:groupIdx' element={<GroupUpdate />}></Route>
      <Route path='/Friend/List' element={<FriendList />}></Route>
      <Route path='/Friend/:friendIdx' element={<FriendDetail />}></Route>
      <Route path='/Friend/Add' element={<FriendAdd />}></Route>
      <Route path='/Alarm' element={<AlarmList />}></Route>
      <Route path='/MyPage' element={<MyPage />}></Route>
      <Route path='/RecycleBin' element={<RecycleBin />}></Route>
      <Route path='*' element={<NotFound />}></Route>
    </Routes>
  );
};

export default AppMain;
