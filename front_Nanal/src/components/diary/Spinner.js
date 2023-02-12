import React from 'react';
import '../../src_assets/css/Spinner.css';

function Spinner(props) {
  return (
    <div className='overlay'>
      <div className='spinner'>{props.children}</div>
    </div>
  );
}

export default Spinner;
