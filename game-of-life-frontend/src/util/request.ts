export default class Request {
    private headers: Headers;
    private url: any;
    private method: any;
    private body: any;

    constructor(url: any, method: any = 'GET', body?: any) {
        this.url = url;
        this.method = method;

        if (body) {
            this.body = body;
            this.headers = new Headers();
            this.headers.append('Content-Type', 'application/json');
        };
    }

    public execute = (): Promise<Response> => {
        let options = {
            method: this.method
        };

        if (this.body) {
            options = Object.assign({
                body: JSON.stringify(this.body),
                headers: this.headers
            }, options);
        }

        return fetch(this.url, options);
    }
}