import axios from 'axios';
import { setInterceptors } from './config/interceptors';

const BASE_URL = 'https://untacttest.ga/api/';

function createInstance() {
  const instance = axios.create({
    baseURL: BASE_URL
  })
  return setInterceptors(instance);
}
const instance = createInstance();

export default instance;