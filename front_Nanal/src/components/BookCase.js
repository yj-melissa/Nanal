import { useState } from "react";
import shelf from "../src_assets/img/shelf.png";
import ang from "../src_assets/img/emo_ang.png"
import calm from "../src_assets/img/emo_calm.png"
import emb from "../src_assets/img/emo_emb.png"
import joy from "../src_assets/img/emo_joy.png"
import nerv from "../src_assets/img/emo_nerv.png"
import sad from "../src_assets/img/emo_sad.png"

function BookCase() {
  const [Data, setData] = useState([
    //이미지랑 아이디만 있으면 될 듯?
  ]);
  const [Collocate, setCollocate] = useState(false)
  const changeCollocate = () => {
    if (Collocate === false) {
      setCollocate(true)
    }
  }
  return (
    <div>
      {/* bookshelf 마진처리 해야함!!! */}
      <br />
      <div>
        <div className="text-center h-20 w-60 p-4 relative top-5 left-10 flex justify-evenly">
          <div className="grid items-center h-16 w-16 relative box-border border-black text-center border-2">
            img
          </div>
          <div className="grid items-center h-16 w-16 relative box-border border-black text-center border-2">
            img
          </div>
        </div>
        {/* 책장 */}
        <img src={shelf} className="mx-auto py-3 w-60" />
        <div className="text-center h-20 w-60 p-4 relative top-5 left-10 flex justify-evenly">
          <div className="grid items-center h-16 w-16 relative box-border border-black text-center border-2">
            img
          </div>
          <div className="grid items-center h-16 w-16 relative box-border border-black text-center border-2">
            img
          </div>
        </div>
        {/* 책장 */}
        <img src={shelf} className="mx-auto py-3 w-60" />
        <div className="text-center h-20 w-60 p-4 relative top-5 left-10 flex justify-evenly">
          <div className="grid items-center h-16 w-16 relative box-border border-black text-center border-2">
            img
          </div>
          <div className="grid items-center h-16 w-16 relative box-border border-black text-center border-2">
            img
          </div>
        </div>
        {/* 책장 */}
        <img src={shelf} className="mx-auto py-3 w-60" />
      </div>
      <div className="flex justify-center">
        <img src={calm} className="w-12 h-12 absolute "/>
        <img src={emb} className="w-12 h-12 absolute "/>
        <img src={nerv} className="w-12 h-12 absolute "/>
        <img src={sad} className="w-12 h-12 absolute "/>
        <img src={ang} className="w-12 h-12 absolute "/>
        <img src={joy} className="w-12 h-12 absolute "/>
        <button className="text-[12px] box-border rounded-full bg-slate-100/50 h-16 w-16 absolute"> 감정 정령들 보기 </button>
      </div>
    </div>
  );
}

export default BookCase;
