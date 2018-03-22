package ru.abelov.schemetestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.abelov.schemeTimeComponent.entity.ISection;
import ru.abelov.schemeTimeComponent.entity.IStatus;
import ru.abelov.schemeTimeComponent.entity.IStore;
import ru.abelov.schemeTimeComponent.entity.ITable;
import ru.abelov.schemeTimeComponent.entity.IUser;
import ru.abelov.schemeTimeComponent.scheme.ControlTableList;
import ru.abelov.schemeTimeComponent.OnTableSelectListener;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.timepicker.DatePickerListener;
import ru.abelov.schemeTimeComponent.timepicker.HorizontalPicker;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cTableList)
    ControlTableList cPlace;

    @BindView(R.id.datePicker)
    HorizontalPicker picker;

    TableStatusData data;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        final SectionEntity section = new Gson().fromJson(
                "{\n" +
                        "   \"id\":77,\n" +
                        "   \"brandId\":4,\n" +
                        "   \"storeId\":8,\n" +
                        "   \"name\":\"Малый зал\",\n" +
                        "   \"schemaImg\":\"/image-resource/schema/it_st_1508833700856_Схема_Fanny_Kabany_студия_1.jpg\",\n" +
                        "   \"adminId\":\"\",\n" +
                        "   \"ordinal\":0,\n" +
                        "   \"tables\":[\n" +
                        "      {\n" +
                        "         \"id\":1028,\n" +
                        "         \"brandId\":4,\n" +
                        "         \"storeId\":8,\n" +
                        "         \"sectionId\":77,\n" +
                        "         \"ordinal\":0,\n" +
                        "         \"name\":\"Столик у окна\",\n" +
                        "         \"enabled\":true,\n" +
                        "         \"orderEnabled\":true,\n" +
                        "         \"posNo\":\"\",\n" +
                        "         \"orderId\":null,\n" +
                        "         \"masterTableNo\":null,\n" +
                        "         \"adminId\":\"\",\n" +
                        "         \"capacity\":6,\n" +
                        "         \"mapX\":308,\n" +
                        "         \"mapY\":74,\n" +
                        "         \"imagePath\":\"http://img.bakapp.ru:80/image/table/instance/file?instance=18\",\n" +
                        "         \"tableStatuses\":null,\n" +
                        "         \"deposit\":1.0,\n" +
                        "         \"used\":false\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":1029,\n" +
                        "         \"brandId\":4,\n" +
                        "         \"storeId\":8,\n" +
                        "         \"sectionId\":77,\n" +
                        "         \"ordinal\":0,\n" +
                        "         \"name\":\"Столик у сцены\",\n" +
                        "         \"enabled\":true,\n" +
                        "         \"orderEnabled\":true,\n" +
                        "         \"posNo\":\"\",\n" +
                        "         \"orderId\":null,\n" +
                        "         \"masterTableNo\":null,\n" +
                        "         \"adminId\":\"\",\n" +
                        "         \"capacity\":6,\n" +
                        "         \"mapX\":508,\n" +
                        "         \"mapY\":254,\n" +
                        "         \"imagePath\":\"http://img.bakapp.ru:80/image/table/instance/file?instance=18\",\n" +
                        "         \"tableStatuses\":null,\n" +
                        "         \"deposit\":4300.0,\n" +
                        "         \"used\":false\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":1296,\n" +
                        "         \"brandId\":4,\n" +
                        "         \"storeId\":8,\n" +
                        "         \"sectionId\":77,\n" +
                        "         \"ordinal\":0,\n" +
                        "         \"name\":\"Златый\",\n" +
                        "         \"enabled\":true,\n" +
                        "         \"orderEnabled\":true,\n" +
                        "         \"posNo\":\"\",\n" +
                        "         \"orderId\":null,\n" +
                        "         \"masterTableNo\":null,\n" +
                        "         \"adminId\":\"\",\n" +
                        "         \"capacity\":4,\n" +
                        "         \"mapX\":310,\n" +
                        "         \"mapY\":311,\n" +
                        "         \"imagePath\":\"http://img.bakapp.ru:80/image/table/instance/file?instance=19\",\n" +
                        "         \"tableStatuses\":null,\n" +
                        "         \"deposit\":1.0,\n" +
                        "         \"used\":false\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":1363,\n" +
                        "         \"brandId\":4,\n" +
                        "         \"storeId\":8,\n" +
                        "         \"sectionId\":77,\n" +
                        "         \"ordinal\":0,\n" +
                        "         \"name\":\"\",\n" +
                        "         \"enabled\":true,\n" +
                        "         \"orderEnabled\":true,\n" +
                        "         \"posNo\":\"\",\n" +
                        "         \"orderId\":null,\n" +
                        "         \"masterTableNo\":null,\n" +
                        "         \"adminId\":\"\",\n" +
                        "         \"capacity\":4,\n" +
                        "         \"mapX\":1031,\n" +
                        "         \"mapY\":254,\n" +
                        "         \"imagePath\":\"http://img.bakapp.ru:80/image/table/instance/file?instance=14\",\n" +
                        "         \"tableStatuses\":null,\n" +
                        "         \"deposit\":0.0,\n" +
                        "         \"used\":false\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":1364,\n" +
                        "         \"brandId\":4,\n" +
                        "         \"storeId\":8,\n" +
                        "         \"sectionId\":77,\n" +
                        "         \"ordinal\":0,\n" +
                        "         \"name\":\"\",\n" +
                        "         \"enabled\":true,\n" +
                        "         \"orderEnabled\":true,\n" +
                        "         \"posNo\":\"\",\n" +
                        "         \"orderId\":null,\n" +
                        "         \"masterTableNo\":null,\n" +
                        "         \"adminId\":\"\",\n" +
                        "         \"capacity\":4,\n" +
                        "         \"mapX\":731,\n" +
                        "         \"mapY\":254,\n" +
                        "         \"imagePath\":\"http://img.bakapp.ru:80/image/table/instance/file?instance=14\",\n" +
                        "         \"tableStatuses\":null,\n" +
                        "         \"deposit\":0.0,\n" +
                        "         \"used\":false\n" +
                        "      }\n" +
                        "   ]\n" +
                        "}"
//            "{\n" +
//                "   \"id\":77,\n" +
//                "   \"brandId\":4,\n" +
//                "   \"storeId\":8,\n" +
//                "   \"name\":\"Малый зал\",\n" +
//                "   \"schemaImg\":\"/image-resource/schema/it_st_1508833700856_Схема_Fanny_Kabany_студия_1.jpg\",\n" +
//                "   \"adminId\":\"\",\n" +
//                "   \"ordinal\":0,\n" +
//                "   \"tables\":[\n" +
//                "      {\n" +
//                "         \"id\":1028,\n" +
//                "         \"brandId\":4,\n" +
//                "         \"storeId\":8,\n" +
//                "         \"sectionId\":77,\n" +
//                "         \"ordinal\":0,\n" +
//                "         \"name\":\"Столик у окна\",\n" +
//                "         \"enabled\":true,\n" +
//                "         \"orderEnabled\":true,\n" +
//                "         \"posNo\":\"\",\n" +
//                "         \"orderId\":null,\n" +
//                "         \"masterTableNo\":null,\n" +
//                "         \"adminId\":\"\",\n" +
//                "         \"capacity\":6,\n" +
//                "         \"mapX\":261,\n" +
//                "         \"mapY\":37,\n" +
//                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
//                "         \"tableStatuses\":[\n" +
//                "            {\n" +
//                "               \"id\":439,\n" +
//                "               \"tableId\":1028,\n" +
//                "               \"userId\":428,\n" +
//                "               \"orderBegin\":1522243215000,\n" +
//                "               \"orderEnd\":1522243515000\n" +
//                "            }\n" +
//                "         ],\n" +
//                "         \"deposit\":1.0,\n" +
//                "         \"used\":false\n" +
//                "      },\n" +
//                "      {\n" +
//                "         \"id\":1029,\n" +
//                "         \"brandId\":4,\n" +
//                "         \"storeId\":8,\n" +
//                "         \"sectionId\":77,\n" +
//                "         \"ordinal\":0,\n" +
//                "         \"name\":\"Столик у сцены\",\n" +
//                "         \"enabled\":true,\n" +
//                "         \"orderEnabled\":true,\n" +
//                "         \"posNo\":\"\",\n" +
//                "         \"orderId\":null,\n" +
//                "         \"masterTableNo\":null,\n" +
//                "         \"adminId\":\"\",\n" +
//                "         \"capacity\":6,\n" +
//                "         \"mapX\":727,\n" +
//                "         \"mapY\":217,\n" +
//                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
//                "         \"tableStatuses\":null,\n" +
//                "         \"deposit\":4300.0,\n" +
//                "         \"used\":false\n" +
//                "      },\n" +
//                "      {\n" +
//                "         \"id\":1296,\n" +
//                "         \"brandId\":4,\n" +
//                "         \"storeId\":8,\n" +
//                "         \"sectionId\":77,\n" +
//                "         \"ordinal\":0,\n" +
//                "         \"name\":\"Златый\",\n" +
//                "         \"enabled\":true,\n" +
//                "         \"orderEnabled\":true,\n" +
//                "         \"posNo\":\"\",\n" +
//                "         \"orderId\":null,\n" +
//                "         \"masterTableNo\":null,\n" +
//                "         \"adminId\":\"\",\n" +
//                "         \"capacity\":4,\n" +
//                "         \"mapX\":627,\n" +
//                "         \"mapY\":57,\n" +
//                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
//                "         \"tableStatuses\":null,\n" +
//                "         \"deposit\":1.0,\n" +
//                "         \"used\":false\n" +
//                "      },\n" +
//                "      {\n" +
//                "         \"id\":1363,\n" +
//                "         \"brandId\":4,\n" +
//                "         \"storeId\":8,\n" +
//                "         \"sectionId\":77,\n" +
//                "         \"ordinal\":0,\n" +
//                "         \"name\":\"\",\n" +
//                "         \"enabled\":true,\n" +
//                "         \"orderEnabled\":true,\n" +
//                "         \"posNo\":\"\",\n" +
//                "         \"orderId\":null,\n" +
//                "         \"masterTableNo\":null,\n" +
//                "         \"adminId\":\"\",\n" +
//                "         \"capacity\":4,\n" +
//                "         \"mapX\":447,\n" +
//                "         \"mapY\":91,\n" +
//                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
//                "         \"tableStatuses\":null,\n" +
//                "         \"deposit\":0.0,\n" +
//                "         \"used\":false\n" +
//                "      },\n" +
//                "      {\n" +
//                "         \"id\":1364,\n" +
//                "         \"brandId\":4,\n" +
//                "         \"storeId\":8,\n" +
//                "         \"sectionId\":77,\n" +
//                "         \"ordinal\":0,\n" +
//                "         \"name\":\"\",\n" +
//                "         \"enabled\":true,\n" +
//                "         \"orderEnabled\":true,\n" +
//                "         \"posNo\":\"\",\n" +
//                "         \"orderId\":null,\n" +
//                "         \"masterTableNo\":null,\n" +
//                "         \"adminId\":\"\",\n" +
//                "         \"capacity\":4,\n" +
//                "         \"mapX\":567,\n" +
//                "         \"mapY\":171,\n" +
//                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
//                "         \"tableStatuses\":null,\n" +
//                "         \"deposit\":0.0,\n" +
//                "         \"used\":false\n" +
//                "      }\n" +
//                "   ]\n" +
//                "}"
                , SectionEntity.class);

        IStore store = new IStore() {
            @Override
            public String getOrderBegin() {
                return "0700";
            }

            @Override
            public String getOrderEnd() {
                return "2300";
            }

            @Override
            public String getTimeFormat() {
                return "hh     :             mm";
            }
        };

        ISection room = new ISection() {
            @Override
            public String getSectionURL() {
                return section.schemaImg;
            }

            @Override
            public List<ITable> getTables() {
                List<ITable> list = new ArrayList<>();
                for(final TableEntity t : section.tables){
                    list.add(new ITable() {
                        @Override
                        public long getId() {
                            return t.id;
                        }

                        @Override
                        public int getX() {
                            return t.mapX;
                        }

                        @Override
                        public int getY() {
                            return t.mapY;
                        }

                        @Override
                        public int getCapacity() {
                            return t.capacity;
                        }

                        @Override
                        public String getImageUrl() {
                            return t.imagePath;
                        }

                        @Override
                        public List<IStatus> getStatuses() {
                            List<IStatus> list = new ArrayList<>();
                            if(t.tableStatuses != null) {
                                for (final TableStatusesEntity s : t.tableStatuses) {
                                    list.add(new IStatus() {
                                        @Override
                                        public int getUserId() {
                                            return s.userId;
                                        }

                                        @Override
                                        public long getOrderBegin() {
                                            return s.orderBegin;
                                        }

                                        @Override
                                        public long getOrderEnd() {
                                            return s.orderEnd;
                                        }
                                    });
                                }
                            }
                            return null;
                        }

                    });
                }
                return list;
            }
        };

        IUser user = new IUser() {
            @Override
            public long getUserId() {
                return 321;
            }
        };

        data = new TableStatusData(this,
                1521723396878L,
                store,
                user,
                1521723396878L,
                1800000L,
                3600000L,
                2,
                getResources().getString(R.string.working_time_format));


        new ControlTableList.Builder(cPlace)
                .setListener(new OnTableSelectListener(){
                    @Override
                    public void onTableSelect(ITable table) {

                    }
                })
                .setTableStatusData(data)
                .setTimeLine(room)
                .build();

        new HorizontalPicker.Builder(picker)
                .setListener(new DatePickerListener() {
                    @Override
                    public void onTimeChanged() {

                    }

                    @Override
                    public void onTimeSelected(long start) {

                    }

                    @Override
                    public void onOrder(long orderStart, long orderStop, ITable table) {

                    }
                })
                .setTimeLine(data.generateTimeLine())
                .setTableStatusData(data)
                .build()
                .setInitialPosition();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}
