import { FaUserCog } from 'react-icons/fa';
import { FaPhone } from 'react-icons/fa6';
import { IoLocationOutline } from 'react-icons/io5';
import { FaRegCalendarAlt } from 'react-icons/fa';
import { FaRegCalendarCheck } from 'react-icons/fa6';
import { FaWeightScale } from 'react-icons/fa6';
import { FaTruck } from 'react-icons/fa';
import { RiMoneyDollarCircleLine } from 'react-icons/ri';
import { TicketStatus } from '@renderer/types/import';
import { FaCircleNotch } from 'react-icons/fa';
import { DataField, Button } from '@renderer/components';

type ExportTicketDetailMainSectionProps = {
    partnerName: string;
    partnerPhone: string;
    address: string;
    dateCreated: string;
    dateApproved: string;
    weight: number;
    transporter: string;
    price: number;
    status: TicketStatus;
    openPopup: () => void;
};

const TICKET_STATUS_STYLE = {
    APPROVED: 'text-emerald-500',
    PENDING: 'text-amber-300',
    REJECTED: 'text-red-500'
};

const TICKET_STATUS_NAME = {
    APPROVED: 'Đã duyệt',
    PENDING: 'Chờ duyệt',
    REJECTED: 'Từ chối'
};

const ExportTicketDetailMainSection = ({
    partnerName,
    partnerPhone,
    address,
    dateCreated,
    dateApproved,
    weight,
    transporter,
    price,
    status,
    openPopup
}: ExportTicketDetailMainSectionProps) => {
    return (
        <div className="flex gap-5 max-w-[900px] mt-5">
            <div className="space-y-5 flex-1">
                <DataField
                    name="Tên đối tác"
                    icon={<FaUserCog />}
                    disabled={false}
                    value={partnerName}
                    defaultValue="Tên đối tác"
                />
                <DataField
                    name={'Liên hệ'}
                    icon={<FaPhone />}
                    disabled={false}
                    value={partnerPhone}
                    defaultValue={'Liên hệ'}
                />
                <DataField
                    name={'Nguồn gốc'}
                    icon={<IoLocationOutline />}
                    disabled={false}
                    value={address}
                    defaultValue={'Nguồn gốc'}
                />
                <DataField
                    name={'Ngày nhập'}
                    icon={<FaRegCalendarAlt />}
                    disabled={false}
                    value={dateCreated}
                    defaultValue={'Tên đối tác'}
                />
                <DataField
                    name={'Ngày duyệt'}
                    icon={<FaRegCalendarCheck />}
                    disabled={false}
                    value={dateApproved}
                    defaultValue={'Ngày duyệt'}
                />
            </div>
            <div className="space-y-5 flex-1">
                <DataField
                    name={'Khối lượng'}
                    icon={<FaWeightScale />}
                    disabled={false}
                    value={`${weight} kg`}
                    defaultValue={'Khối lượng'}
                />
                <DataField
                    name={'Vận chuyển'}
                    icon={<FaTruck />}
                    disabled={false}
                    value={transporter}
                    defaultValue={'Vận chuyển'}
                />
                <DataField
                    name={'Giá trị'}
                    icon={<RiMoneyDollarCircleLine />}
                    disabled={false}
                    value={`${price} VND`}
                    defaultValue={'Giá trị'}
                />
                <DataField
                    name="Trạng thái"
                    icon={<FaCircleNotch />}
                    disabled={false}
                    value={TICKET_STATUS_NAME[status]}
                    defaultValue={'Trạng thái'}
                    textColor={TICKET_STATUS_STYLE[status]}
                />
                {status === TicketStatus.Pending && (
                    <Button
                        className="px-2 py-1 border-emerald-500 text-emerald-500 border rounded-md font-semibold w-fit ml-auto"
                        text="Duyệt phiếu"
                        action={openPopup}
                    />
                )}
            </div>
        </div>
    );
};

export default ExportTicketDetailMainSection;
