import bookmarksRed from "../../src_assets/img/bookmarksRed.png"
import groupYellow from "../../src_assets/img/groupYellow.png"
import personGreen from "../../src_assets/img/personGreen.png"


function Settings() {
  return <div className="flex justify-evenly">
    <div className="grid content-evenly">
      <img src={bookmarksRed} className="m-auto"/>
      <p className="text-center my-1">그룹 관리</p>
    </div>
    <div className="grid content-evenly">
      <img src={groupYellow} className="m-auto"/>
      <p className="text-center my-1">친구 목록</p>
    </div>
    <div className="grid content-evenly">
      <img src={personGreen} className="m-auto"/>
      <p className="text-center my-1">프로필 설정</p>
    </div>
  </div>
}

export default Settings