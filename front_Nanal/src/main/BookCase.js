import { useState } from "react";
import shelf from "../src_assets/img/shelf.png";

function BookCase() {
  const [Data, setData] = useState([
    //이미지랑 아이디만 있으면 될 듯?
  ]);
  return (
    <div>
      {/* bookshelf 마진처리 해야함!!! */}
      <br />
      <div>
        <div className="text-center h-20 w-60 p-4 relative top-12 left-10 flex justify-evenly">
          <div className="grid items-center box-border border-black text-center h-16 w-16 border-2 relative">
            img
          </div>
          <div className="grid items-center box-border border-black text-center h-16 w-16 border-2 relative">
            img
          </div>
        </div>
        <img src={shelf} className="my-5 mx-auto py-5 w-60" />
        <div className="text-center h-20 w-60 p-4 relative top-12 left-10 flex justify-evenly">
          <div className="grid items-center box-border border-black text-center h-16 w-16 border-2 relative">
            img
          </div>
          <div className="grid items-center box-border border-black text-center h-16 w-16 border-2 relative">
            img
          </div>
        </div>
        <img src={shelf} className="my-5 mx-auto py-5 w-60" />
        <div className="text-center h-20 w-60 p-4 relative top-12 left-10 flex justify-evenly">
          <div className="grid items-center box-border border-black text-center h-16 w-16 border-2 relative">
            img
          </div>
          <div className="grid items-center box-border border-black text-center h-16 w-16 border-2 relative">
            img
          </div>
        </div>
        <img src={shelf} className="my-5 mx-auto py-5 w-60" />
      </div>
    </div>
  );
}

export default BookCase;
