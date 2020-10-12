import React from 'react';
import Authentication from '../components/Tryout/Authentication';
import TryoutHeader from '../components/Tryout/TryoutHeader';
import TryoutWaiting from '../components/Tryout/TryoutWaiting';
import TryoutAgreement from '../components/Tryout/TryoutAgreement';
import Tryout from '../components/Tryout/Tryout';
import TryoutFinish from '../components/Tryout/TryoutFinish';
import NotFoundPage from './NotFoundPage';
import { Switch, withRouter } from 'react-router';
import { Route } from 'react-router-dom';

const TryoutPage = ({ location }) => {
  const authenticationStatus = location.pathname.split('/').length == 4 && !location.pathname.includes('finish')

  return (
    <>
      {authenticationStatus && <TryoutHeader />}
      <Switch>
        <Route path="/tryout/:examId" render={() => <Authentication />} exact />
        <Route path="/tryout/:examId/wait" render={() => <TryoutWaiting />}/>
        <Route path="/tryout/:examId/agreement" render={() => <TryoutAgreement />}/>
        <Route path="/tryout/:examId/test" render={() => <Tryout />}/>
        <Route path="/tryout/:examId/finish" render={() => <TryoutFinish />}/>
        <Route component={NotFoundPage} />
      </Switch>
    </>
  );
};

export default withRouter(TryoutPage);