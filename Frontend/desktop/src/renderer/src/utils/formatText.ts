import moment from 'moment';

// format number
// ex: 1000000 => 1.000.000
export const formatNumber = (num: number) =>
    num.toLocaleString('en-US').replaceAll(',', '.');

export const formatDate = (date: string) => moment(date).format('DD/MM/YYYY');

export const formatDateTime = (date: string) =>
    moment(date).format('DD/MM/YYYY HH:mm:ss');
