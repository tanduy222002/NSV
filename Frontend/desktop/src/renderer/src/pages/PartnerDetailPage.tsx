import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { UserInfo, Button, PageLoading } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { getPartnerDetail } from '@renderer/services/api';
import { FaEdit } from 'react-icons/fa';
import {
    PartnerDetailMainSection,
    PartnerDetailTransactionSection,
    PartnerDetailDebtSection
} from '@renderer/features/partner';
import partnerIconSrc from '@renderer/assets/partner-icon.png';
import { cn } from '@renderer/utils/util';

enum PartnerDetailSection {
    Debt = 'Debt',
    Transaction = 'Transaction'
}

const PartnerDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const goToPartnerPage = () => navigate('/partner');
    const goToEditPartnerPage = () => navigate(`/partner/${id}/edit`);

    const [viewedSection, setViewedSection] = useState(
        PartnerDetailSection.Transaction
    );

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['partner', id],
        queryFn: () =>
            getPartnerDetail({
                token: accessToken,
                partnerId: id as string
            })
    });

    if (!isFetching) console.log('data: ', data);

    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToPartnerPage}
                />
                <img alt="form-icon" src={partnerIconSrc} />
                <h1 className="text-xl font-semibold">Đối tác</h1>
                <FaEdit
                    className="w-[20px] h-[20px] text-amber-300 cursor-pointer"
                    onClick={() => goToEditPartnerPage()}
                />
            </div>
            {isFetching ? (
                <PageLoading />
            ) : (
                <>
                    <PartnerDetailMainSection
                        avatar={data?.avatar}
                        name={data?.name}
                        phoneNumber={data?.phone}
                        email={data?.email}
                        address={data?.address_string}
                        bankAccount={data?.bankAccount}
                        taxNumber={data?.taxNumber}
                        totalTransactionCount={data?.total_transaction}
                        totalTransactionValue={data?.total_transaction_amount}
                    />
                    <div className="flex space-x-3 my-5">
                        <Button
                            className={cn(
                                'px-2 py-1 border rounded-md font-semibold w-fit',
                                viewedSection ===
                                    PartnerDetailSection.Transaction
                                    ? 'bg-sky-800 text-white'
                                    : 'border-sky-800 text-sky-800'
                            )}
                            text="Giao dịch"
                            action={() =>
                                setViewedSection(
                                    PartnerDetailSection.Transaction
                                )
                            }
                        />
                        <Button
                            className={cn(
                                'px-2 py-1 border rounded-md font-semibold w-fit',
                                viewedSection === PartnerDetailSection.Debt
                                    ? 'bg-sky-800 text-white'
                                    : 'border-sky-800 text-sky-800'
                            )}
                            text="Công nợ"
                            action={() =>
                                setViewedSection(PartnerDetailSection.Debt)
                            }
                        />
                    </div>
                    {viewedSection === PartnerDetailSection.Transaction ? (
                        <PartnerDetailTransactionSection />
                    ) : (
                        <PartnerDetailDebtSection />
                    )}
                </>
            )}
        </div>
    );
};

export default PartnerDetailPage;
