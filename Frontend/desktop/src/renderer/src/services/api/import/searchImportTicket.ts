import { TicketStatus } from '@renderer/types/import';
import { makeAuthRequest } from '../makeRequest';

type SearchImportTicketPayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    name?: string;
    status?: TicketStatus;
};

export const searchImportTicket = async ({
    token,
    pageIndex,
    pageSize = 12,
    name,
    status
}: SearchImportTicketPayload) => {
    try {
        const response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/search`,
            method: 'get',
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                name: name,
                type: 'IMPORT',
                status: status !== TicketStatus.All ? status : undefined
            }
        });
        return response;
    } catch (err) {
        console.log('get import ticket error: ', err);
    }
};
