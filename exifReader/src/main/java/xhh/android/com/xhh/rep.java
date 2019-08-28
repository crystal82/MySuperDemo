package xhh.android.com.xhh;

import java.util.List;

/**
 * Created by Administrator on 2018-02-11.
 */

public class rep {

    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * regeocode : {"formatted_address":"四川省乐山市峨边彝族自治县黑竹沟镇","addressComponent":{"country":"中国","province":"四川省","city":"乐山市","citycode":"0833","district":"峨边彝族自治县","adcode":"511132","township":"黑竹沟镇","towncode":"511132105000","neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"streetNumber":{"street":[],"number":[],"direction":[],"distance":[]},"businessAreas":[[]]}}
     */

    private String status;
    private String info;
    private String infocode;
    private RegeocodeBean regeocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public RegeocodeBean getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(RegeocodeBean regeocode) {
        this.regeocode = regeocode;
    }

    public static class RegeocodeBean {
        /**
         * formatted_address : 四川省乐山市峨边彝族自治县黑竹沟镇
         * addressComponent : {"country":"中国","province":"四川省","city":"乐山市","citycode":"0833","district":"峨边彝族自治县","adcode":"511132","township":"黑竹沟镇","towncode":"511132105000","neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"streetNumber":{"street":[],"number":[],"direction":[],"distance":[]},"businessAreas":[[]]}
         */

        private String formatted_address;
        private AddressComponentBean addressComponent;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public static class AddressComponentBean {
            /**
             * country : 中国
             * province : 四川省
             * city : 乐山市
             * citycode : 0833
             * district : 峨边彝族自治县
             * adcode : 511132
             * township : 黑竹沟镇
             * towncode : 511132105000
             * neighborhood : {"name":[],"type":[]}
             * building : {"name":[],"type":[]}
             * streetNumber : {"street":[],"number":[],"direction":[],"distance":[]}
             * businessAreas : [[]]
             */

            private String country;
            private String province;
            private String city;
            private String citycode;
            private String district;
            private String adcode;
            private String township;
            private String towncode;
            private NeighborhoodBean neighborhood;
            private BuildingBean building;
            private StreetNumberBean streetNumber;
            private List<List<?>> businessAreas;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getTownship() {
                return township;
            }

            public void setTownship(String township) {
                this.township = township;
            }

            public String getTowncode() {
                return towncode;
            }

            public void setTowncode(String towncode) {
                this.towncode = towncode;
            }

            public NeighborhoodBean getNeighborhood() {
                return neighborhood;
            }

            public void setNeighborhood(NeighborhoodBean neighborhood) {
                this.neighborhood = neighborhood;
            }

            public BuildingBean getBuilding() {
                return building;
            }

            public void setBuilding(BuildingBean building) {
                this.building = building;
            }

            public StreetNumberBean getStreetNumber() {
                return streetNumber;
            }

            public void setStreetNumber(StreetNumberBean streetNumber) {
                this.streetNumber = streetNumber;
            }

            public List<List<?>> getBusinessAreas() {
                return businessAreas;
            }

            public void setBusinessAreas(List<List<?>> businessAreas) {
                this.businessAreas = businessAreas;
            }

            public static class NeighborhoodBean {
                private List<?> name;
                private List<?> type;

                public List<?> getName() {
                    return name;
                }

                public void setName(List<?> name) {
                    this.name = name;
                }

                public List<?> getType() {
                    return type;
                }

                public void setType(List<?> type) {
                    this.type = type;
                }
            }

            public static class BuildingBean {
                private List<?> name;
                private List<?> type;

                public List<?> getName() {
                    return name;
                }

                public void setName(List<?> name) {
                    this.name = name;
                }

                public List<?> getType() {
                    return type;
                }

                public void setType(List<?> type) {
                    this.type = type;
                }
            }

            public static class StreetNumberBean {
                private List<?> street;
                private List<?> number;
                private List<?> direction;
                private List<?> distance;

                public List<?> getStreet() {
                    return street;
                }

                public void setStreet(List<?> street) {
                    this.street = street;
                }

                public List<?> getNumber() {
                    return number;
                }

                public void setNumber(List<?> number) {
                    this.number = number;
                }

                public List<?> getDirection() {
                    return direction;
                }

                public void setDirection(List<?> direction) {
                    this.direction = direction;
                }

                public List<?> getDistance() {
                    return distance;
                }

                public void setDistance(List<?> distance) {
                    this.distance = distance;
                }
            }
        }
    }
}
