export type SelectOption = {
    id: number;
    name: string;
};

export type InfoPopup = {
    title: string;
    body: string;
};

export enum ResultPopupType {
    Success = 'Success',
    Error = 'Error',
    Info = 'Info'
}

export type ResultPopup = {
    title: string;
    body: string;
    popupType: ResultPopupType;
};

export type Location = {
    id: number;
    name: string;
};
