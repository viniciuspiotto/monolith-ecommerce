import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';

const BASE_URL = 'http://localhost:8080/api/v1';

const errorRate = new Counter('error_rate');

export const options = {
    scenarios: {
        search_models: {
            executor: 'constant-arrival-rate',
            rate: 50,
            timeUnit: '1s',
            duration: '2m',
            preAllocatedVUs: 10,
            maxVUs: 50,
            exec: 'searchModels',
        },
        add_to_cart: {
            executor: 'constant-arrival-rate',
            rate: 10,
            timeUnit: '1s',
            duration: '2m',
            preAllocatedVUs: 5,
            maxVUs: 20,
            exec: 'addToCart',
        },
    },
    thresholds: {
        'http_req_duration{scenario:search_models}': ['p(95)<500'],
        'http_req_duration{scenario:add_to_cart}': ['p(95)<800'],
        'error_rate': ['count<20'],
    },
};

export function searchModels() {
    const searchTerms = ['autem', 'qui', 'fuga', 'iste', 'aut'];
    const randomTerm = searchTerms[Math.floor(Math.random() * searchTerms.length)];

    const res = http.get(`${BASE_URL}/models/search?q=${randomTerm}`);

    const success = check(res, {
        'search status is 200': (r) => r.status === 200,
    });
    if (!success) {
        errorRate.add(1);
    }
    sleep(1);
}

export function addToCart() {
    const payload = JSON.stringify({
        modelId: '79477ff3-f08d-446c-9f87-5f705b35e297',
        quantity: 1
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        }
    };

    const res = http.post(`${BASE_URL}/carts/items`, payload, params);

    const success = check(res, {
        'add to cart status is 200': (r) => r.status === 200,
    });
    if (!success) {
        errorRate.add(1);
    }
    sleep(2);
}