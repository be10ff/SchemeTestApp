package ru.abelov.schemetestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
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

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnRefresh:
                init();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {

        final Store store =  new Gson().fromJson(
                "{\n" +
                "   \"id\":8,\n" +
                "   \"brandId\":4,\n" +
                "   \"storeNm\":\"Bak coffee\",\n" +
                "   \"tel\":\"+7-495-111-11-11\",\n" +
                "   \"city\":\"Москва\",\n" +
                "   \"addr1\":\"ул. Верейская д.29, стр. 134\",\n" +
                "   \"addr2\":\"\",\n" +
                "   \"zip\":\"\",\n" +
                "   \"latitude\":55.7095,\n" +
                "   \"longitude\":37.4423,\n" +
                "   \"timezone\":\"Europe/Moscow\",\n" +
                "   \"tip\":\"{\\\"beacon\\\": true, \\\"parking\\\": true, \\\"babyChair\\\": true, \\\"smallRoom\\\": true, \\\"party\\\": true, \\\"outdoor\\\": true}\",\n" +
                "   \"intro\":\"Когда на душе тоскливо, но в бар совсем не хочется, для вас открываются двери уютных кофеен. Полумрак  и ненавязчивый, отзывчивый персонал укроет вас от любых вьюг\",\n" +
                "   \"distance\":184.22253023131594,\n" +
                "   \"useReview\":true,\n" +
                "   \"useSelforder\":true,\n" +
                "   \"useReserve\":true,\n" +
                "   \"useParty\":true,\n" +
                "   \"rsvDeposit\":100.0,\n" +
                "   \"prtDeposit\":2000.0,\n" +
                "   \"orderBegin\":\"0700\",\n" +
                "   \"orderEnd\":\"0300\",\n" +
                "   \"budget\":\"0\",\n" +
                "   \"imgsUrl\":[\n" +
                "\n" +
                "   ],\n" +
                "   \"coupons\":[\n" +
                "\n" +
                "   ],\n" +
                "   \"externalId\":null,\n" +
                "   \"externalType\":null,\n" +
                "   \"externalMinPrice\":null,\n" +
                "   \"imageUrl\":\"/image-resource/store/discount/8/it_st_1514473592711_586fb3503d79b1597454717c.png\",\n" +
                "   \"nearestMetroStation\":\"\",\n" +
                "   \"rating\":4.07,\n" +
                "   \"ratingCount\":15,\n" +
                "   \"currency\":\"RUB\",\n" +
                "   \"taxRate\":10.0,\n" +
                "   \"bookmark\":0,\n" +
                "   \"logoImg\":\"/image-resource/store/brand/4/1514190179001_j235Ikor.jpg\",\n" +
                "   \"weekSchedule\":{\n" +
                "      \"days\":[\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"1\",\n" +
                "            \"work\":true,\n" +
                "            \"orderBegin\":\"0900\",\n" +
                "            \"orderEnd\":\"2300\",\n" +
                "            \"breaks\":[\n" +
                "               {\n" +
                "                  \"breakBegin\":\"2100\",\n" +
                "                  \"breakEnd\":\"2200\"\n" +
                "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"5\",\n" +
                "            \"work\":true,\n" +
                "            \"orderBegin\":\"1700\",\n" +
                "            \"orderEnd\":\"2400\",\n" +
                "            \"breaks\":[\n" +
                        "               {\n" +
                        "                  \"breakBegin\":\"2100\",\n" +
                        "                  \"breakEnd\":\"2200\"\n" +
                        "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"6\",\n" +
                "            \"work\":true,\n" +
                "            \"orderBegin\":\"0000\",\n" +
                "            \"orderEnd\":\"0700\",\n" +
                "            \"breaks\":[\n" +
                        "               {\n" +
                        "                  \"breakBegin\":\"2100\",\n" +
                        "                  \"breakEnd\":\"2200\"\n" +
                        "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"1\",\n" +
                "            \"work\":true,\n" +
                "            \"orderBegin\":\"0900\",\n" +
                "            \"orderEnd\":\"2300\",\n" +
                "            \"breaks\":[\n" +
                "               {\n" +
                "                  \"breakBegin\":\"1100\",\n" +
                "                  \"breakEnd\":\"1300\"\n" +
                "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"2\",\n" +
                "            \"work\":true,\n" +
                "            \"orderBegin\":\"1000\",\n" +
                "            \"orderEnd\":\"2200\",\n" +
                "            \"breaks\":[\n" +
                "               {\n" +
                "                  \"breakBegin\":\"1200\",\n" +
                "                  \"breakEnd\":\"1230\"\n" +
                "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"2\",\n" +
                "            \"work\":true,\n" +
                "            \"orderBegin\":\"1000\",\n" +
                "            \"orderEnd\":\"2200\",\n" +
                "            \"breaks\":[\n" +
                "               {\n" +
                "                  \"breakBegin\":\"1200\",\n" +
                "                  \"breakEnd\":\"1230\"\n" +
                "               }\n" +
                "            ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"dayOfWeek\":\"3\",\n" +
                "            \"work\":false,\n" +
                "            \"orderBegin\":\"--\",\n" +
                "            \"orderEnd\":\"--\",\n" +
                "            \"breaks\":[\n" +
                        "               {\n" +
                        "                  \"breakBegin\":\"2100\",\n" +
                        "                  \"breakEnd\":\"2200\"\n" +
                        "               }\n" +
                "            ]\n" +
                "         }\n" +
                "      ]\n" +
                "   }\n" +
                "}"
                , Store.class);

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
                , SectionEntity.class);

//        IStore store = new IStore() {
//            @Override
//            public String getOrderBegin() {
//                return "0700";
//            }
//
//            @Override
//            public String getOrderEnd() {
//                return "2300";
//            }
//
//            @Override
//            public String getTimeFormat() {
//                return "hh     :             mm";
//            }
//        };

        ISection room = new ISection() {
            @Override
            public String getSectionURL() {
                return "http://95.131.29.211" + "/" + section.schemaImg;
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
                        public double getDeposit() {
                            return 0;
                        }

                        @Override
                        public String getImageUrl() {
                            return t.imagePath;
                        }

                        @Override
                        public String getName() {
                            return t.name;
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
                            return list;
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

        Calendar c = (Calendar) Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, 5);

        data = new TableStatusData(
                c.getTimeInMillis(),
                store,
                user,
                c.getTimeInMillis(),
                1800000L,
                3600000L,
                2,
                getResources().getString(R.string.working_time_format));


        new ControlTableList.Builder(cPlace)
                .setListener(new OnTableSelectListener(){
                    @Override
                    public void onTableSelect(ITable table) {
                        long i = table.getId();
                        data.setSelectedTable(table);
                        picker.update();
                    }
                })
                .setTableStatusData(data)
                .setTimeLine(room)
                .build();

        new HorizontalPicker.Builder(picker)
                .setListener(new DatePickerListener() {
                    @Override
                    public void onTimeChanged() {
                        cPlace.onTimeChanged();
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
