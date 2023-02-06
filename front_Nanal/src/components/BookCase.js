import { useState } from 'react';
import shelf from '../src_assets/img/shelf.png';
import ang from '../src_assets/img/emo_ang.png';
import calm from '../src_assets/img/emo_calm.png';
import emb from '../src_assets/img/emo_emb.png';
import joy from '../src_assets/img/emo_joy.png';
import nerv from '../src_assets/img/emo_nerv.png';
import sad from '../src_assets/img/emo_sad.png';
import addIcon from '../src_assets/img/file_add_icon.png';
import { Link } from 'react-router-dom';

function BookCase() {
  const [Data, setData] = useState([
    //이미지랑 아이디만 있으면 될 듯?
  ]);
  const [Collocate, setCollocate] = useState(false);
  const changeCollocate = () => {
    setCollocate((Collocate) => !Collocate);
  };
  // img태그가 갖는 공통 css 속성
  const emoCss = 'w-12 h-12 absolute'; // standart top-100, left-26
  const joyCss =
    Collocate === false
      ? 'animate-pulse'
      : 'animate-bounce hover:animate-none top-24 left-12';
  const calmCss =
    Collocate === false
      ? 'animate-pulse'
      : 'animate-bounce hover:animate-none top-28 right-12';
  const nervCss =
    Collocate === false
      ? 'animate-pulse'
      : 'animate-bounce hover:animate-none top-64 right-8';
  const angCss =
    Collocate === false
      ? 'animate-pulse'
      : 'animate-bounce hover:animate-none top-80 left-24';
  const embCss =
    Collocate === false
      ? 'animate-pulse'
      : 'animate-bounce hover:animate-none top-88 left-24';
  const sadCss =
    Collocate === false
      ? 'animate-pulse'
      : 'animate-bounce hover:animate-none top-88 right-24';
  return (
    <div>
      {/* bookshelf 마진처리 해야함!!! */}
      <br />
      <div>
        <div className='text-center h-20 w-60 p-4 relative top-5 left-10 flex justify-evenly'>
          <div className='grid items-center h-16 w-16 relative box-border border-black text-center border-2'>
            개인
          </div>
          <div className='grid items-center h-16 w-16 relative box-border border-black text-center border-2'>
            그룹1
          </div>
        </div>
        {/* 책장 */}
        <img src={shelf} className='mx-auto py-3 w-60' />
        <div className='text-center h-20 w-60 p-4 relative top-5 left-10 flex justify-evenly'>
          <div className='grid items-center h-16 w-16 relative box-border border-black text-center border-2'>
            그룹2
          </div>
          <div className='grid items-center h-16 w-16 relative box-border border-black text-center border-2'>
            그룹3
          </div>
        </div>
        {/* 책장 */}
        <img src={shelf} className='mx-auto py-3 w-60' />
        <div className='text-center h-20 w-60 p-4 relative top-5 left-10 flex justify-evenly'>
          <div className='grid items-center h-16 w-16 relative box-border border-black text-center border-2'>
            그룹4
          </div>

          <Link
            to='/Group/List'
            className='grid items-center h-12 w-10 relative text-center'
          >
            <img src={addIcon} className='m-auto mt-2' />
          </Link>
        </div>
        {/* 책장 */}
        <img src={shelf} className='mx-auto py-3 w-60' />
      </div>
      <div className='flex justify-center'>
        <img src={sad} className={`${emoCss} ${sadCss}`} />
        <img src={nerv} className={`${emoCss} ${nervCss}`} />
        <img src={emb} className={`${emoCss} ${embCss}`} />
        <img src={ang} className={`${emoCss} ${angCss}`} />
        <img src={calm} className={`${emoCss} ${calmCss}`} />
        <img src={joy} className={`${emoCss} ${joyCss}`} />
        <button
          className='text-[12px] box-border rounded-full bg-slate-100/50 h-16 w-16 absolute'
          onClick={changeCollocate}
        >
          <p>
            {Collocate === false ? '감정 정령들 보기' : '감정 정령들 되돌리기'}
          </p>
        </button>
      </div>
    </div>
  );
}

export default BookCase;
