import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';

export const AccountStatusText = {
    ACTIVE: 'Đang hoạt động',
    SUSPENDED: 'Tạm dừng'
};

export const AccountStatusTextColor = {
    ACTIVE: 'text-emerald-500',
    SUSPENDED: 'text-red-500'
};

export const AccountRoleText = {
    ROLE_EMPLOYEE: 'Nhân viên',
    ROLE_MANAGER: 'Thủ kho',
    ROLE_ADMIN: 'Admin'
};

export const deleteEmployeeConfirmPopupData: InfoPopup = {
    title: 'Xóa người dùng',
    body: 'Bạn chắc chắn muốn xóa người dùng này?'
};

type DeleteEmployeeResultType = {
    Success: ResultPopup;
    Error: ResultPopup;
};

export const DeleteEmployeeResult: DeleteEmployeeResultType = {
    Success: {
        title: 'Thành công',
        body: 'Ngừời dùng đã được xóa khỏi hệ thống',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Xóa người dùng thất bại, vui lòng thử lại',
        popupType: ResultPopupType.Error
    }
};

export const updateAccountStatusConfirmPopupData: InfoPopup = {
    title: 'Cập nhật trạng thái',
    body: 'Bạn chắc chắn muốn cập nhật trạng thái tài khoản?'
};

type UpdateAccountStatusResultType = {
    Success: ResultPopup;
    Error: ResultPopup;
};

export const UpdateAccountStatusResult: UpdateAccountStatusResultType = {
    Success: {
        title: 'Thành công',
        body: 'Cập nhật trạng thái tài khoản thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Cập nhật trạng thái tài khoản thất bại',
        popupType: ResultPopupType.Error
    }
};

export const updateAccountRolesResultPopupData: InfoPopup = {
    title: 'Cập nhật trạng thái',
    body: 'Bạn chắc chắn muốn thay đổi quyền người dùng?'
};

type UpdateAccountRolesResultType = {
    Success: ResultPopup;
    Error: ResultPopup;
};

export const UpdateAccountRolesResult: UpdateAccountRolesResultType = {
    Success: {
        title: 'Thành công',
        body: 'Cập nhật quyền người dùng thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Cập nhật quyền người dùng thất bại',
        popupType: ResultPopupType.Error
    }
};
