import { InfoPopup, ResultPopupType } from '@renderer/types/common';
import { Product } from '@renderer/types/product';

export const productDefaultValue: Product = {
    name: '',
    variety: '',
    image: undefined,
    categories: []
};

export const createProductPopupData: InfoPopup = {
    title: 'Tạo sản phẩm',
    body: 'Xác nhận thông tin sản phẩm muốn tạo?'
};

export const CreateProductResult = {
    Success: {
        title: 'Thành công',
        body: 'Tạo sản phẩm thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Tạo sản phẩm thất bại',
        popupType: ResultPopupType.Error
    }
};
