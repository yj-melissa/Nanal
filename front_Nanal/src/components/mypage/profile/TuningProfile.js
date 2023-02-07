import downArrow from '../../../src_assets/img/arrow_drop_down.png'

const TuningProfile = () => {
  return <div className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75' > 
  <div className="self-center">프로필 수정</div>
  <img src={downArrow} className='self-center'/>
</div>
}

export default TuningProfile