import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
  vus: 30,
  duration: '20s',
};

export default function () {
  let res = http.get('http://localhost:8081/api/test/order/after');
  if (res.status !== 200) {
    console.log(`status=${res.status}`);
  }
  sleep(1);
}

