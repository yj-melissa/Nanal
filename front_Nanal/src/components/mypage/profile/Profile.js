import emptyProfile from "../../../src_assets/img/emptyProfile.png"
import InputBox from "./InputBox"

function Profile() {
  return <div>
    <div className="flex justify-evenly mt-5">
      <img src={emptyProfile} className="w-36 h-28 p-1"/>
      <p className="my-auto text-2xl font-bold p-1">유저 님의 일기장</p>
    </div>
    <div className="my-3">
      <p>유저가 설정하는 메시지1</p>
      <InputBox />
      <p className="font-semibold">일기 시작한지 N일째 입니다.</p>
    </div>
    <div className="flex justify-around box-border h-24 w-80 p-4 bg-slate-300/50">
      <div className="grid content-evenly">
        <p className="text-center">총 작성한 일기</p>
        <p className="text-center font-bold">777</p>
      </div>
      <div className="grid content-evenly">
        <p className="text-center">좋아하는 일기</p>
        <p className="text-center font-bold">32</p>
      </div>
    </div>
  </div>
}

export default Profile