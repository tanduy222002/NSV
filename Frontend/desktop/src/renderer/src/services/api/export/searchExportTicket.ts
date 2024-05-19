import { TicketStatus } from '@renderer/types/export';
import { makeAuthRequest } from '../makeRequest';

type SearchExportTicketPayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    name?: string;
    status?: TicketStatus;
};

export const searchExportTicket = async ({
    token,
    pageIndex,
    pageSize = 12,
    name,
    status
}: SearchExportTicketPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/search`,
            method: 'get',
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                name: name,
                type: 'EXPORT',
                status: status !== TicketStatus.All ? status : undefined
            }
        });
        console.log('export tickets: ', response);
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
