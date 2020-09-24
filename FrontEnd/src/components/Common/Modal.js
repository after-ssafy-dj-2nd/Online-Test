import React from 'react';
import './Modal.css';
import ReactTransitionGroup from 'react-addons-css-transition-group';

const Modal = (props) => {
  const { isShow, close, title, children } = props;

  return (
    <ReactTransitionGroup
      transitionName={'modal-anim'}
      transitionEnterTimeout={200}
      transitionLeaveTimeout={200}
    >
      {isShow
        ? (<div className="modal-overlay">
            <div className="modal">
              <div className="modal-title">
                { title }
                <i className="fas fa-times" onClick={close} />
              </div>
              <div className="modal-contents">
                { children }
              </div>
              <div className="button-wrapper">
                <button onClick={close} className="btn btn--xsmall">CLOSE</button>
              </div>
            </div>
          </div>)
        : null
      }
    </ReactTransitionGroup>
  );
};

export default Modal;