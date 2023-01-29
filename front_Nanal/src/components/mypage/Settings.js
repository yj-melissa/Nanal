import { Link } from "react-router-dom"
import bookmarksRed from "../../src_assets/img/bookmarksRed.png"
import groupYellow from "../../src_assets/img/groupYellow.png"
// import personGreen from "../../src_assets/img/personGreen.png"
import recycleBinImg from "../../src_assets/img/recycleBin.png"

function Settings() {
  return <div className="flex justify-evenly">
    <Link to="/Group/List" className="grid content-evenly">
      <img src={bookmarksRed} className="m-auto"/>
      <p className="text-center my-1">그룹 관리</p>
    </Link>
    <Link to="/Friend/List" className="grid content-evenly">
      <img src={groupYellow} className="m-auto"/>
      <p className="text-center my-1">친구 목록</p>
    </Link>
    <Link to="/RecycleBin" className="grid content-evenly">
      <img src={recycleBinImg} className="m-auto"/>
      <p className="text-center my-1">휴지통</p>
    </Link>
  </div>
}

export default Settings