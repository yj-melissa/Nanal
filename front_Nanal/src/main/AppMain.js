import { Routes, Route } from 'react-router-dom';
import Calendar from '../components/Calendaar.js';
import BookCase from '../components/BookCase.js';
import Alarm from '../components/another/Alarm.js';
import SignIn from '../components/account/SignIn.js';
import SignUp from '../components/account/SignUp.js';
import DiaryCreate from '../components/diary/DiaryCreate';
import DiaryList from '../components/diary/DiaryList';
import GroupCreate from '../components/group/GroupCreate';
import GroupList from '../components/group/GroupList';
import GroupDetail from '../components/group/GroupDetail';
import FriendList from '../components/friend/FriendList';
import MyPage from '../components/mypage/MyPage.js';
import RecycleBin from '../components/another/recycleBin';
import NotFound from '../components/another/NotFound.js';
import DiaryDetail from '../components/diary/DiaryDetail.js';

const AppMain = ({ isCalendaar }) => {
  return (
    <Routes>
      {isCalendaar ? (
        <Route path='/' element={<Calendar />}></Route>
      ) : (
        <Route path='/' element={<BookCase />}></Route>
      )}
      <Route path='/Alarm' element={<Alarm />}></Route>
      <Route path='/SignUp' element={<SignUp />}></Route>
      <Route path='/SignIn' element={<SignIn />}></Route>
      <Route path='/Diary/Create' element={<DiaryCreate />}></Route>
      <Route path='/Diary/List' element={<DiaryList />}></Route>
      <Route path='/Diary/:diaryIdx' element={<DiaryDetail />}></Route>
      <Route path='/Group/Create' element={<GroupCreate />}></Route>
      <Route path='/Group/List' element={<GroupList />}></Route>
      <Route path='/Group/:groupIdx' element={<GroupDetail />}></Route>
      <Route path='/Friend/List' element={<FriendList />}></Route>
      <Route path='/MyPage' element={<MyPage />}></Route>
      <Route path='/RecycleBin' element={<RecycleBin />}></Route>
      <Route path='*' element={<NotFound />}></Route>
    </Routes>
  );
};

export default AppMain;
