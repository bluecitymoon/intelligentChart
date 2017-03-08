(function () {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Job',
        'PersonAreaPercentage', 'PersonInnovation', 'PersonExperience', 'PersonEducationBackground', 'PersonConnectionLevel',
        'PersonPopularity', 'PersonPrize', 'lodash', 'PersonRegionConnection', 'PersonWordCloud', 'PersonLawBusiness', 'PersonCreditCardActivity',
        'PersonNetworkDebit', 'PersonNetworkShopping', 'PersonSocialMedia', 'PersonNetworkTexiActivity'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Job, PersonAreaPercentage,
                                    PersonInnovation, PersonExperience, PersonEducationBackground, PersonConnectionLevel,
                                    PersonPopularity, PersonPrize, lodash, PersonRegionConnection, PersonWordCloud, PersonLawBusiness, PersonCreditCardActivity,
                                    PersonNetworkDebit, PersonNetworkShopping, PersonSocialMedia, PersonNetworkTexiActivity) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personUpdate', function (event, result) {
            vm.person = result;
        });

        var singleChartWidth = 300;
        var singleChartHeight = 320;
        $scope.areaConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'
        };

        $scope.areaNewMediaConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.connectionLevelConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: 350,
            theme: 'macarons'

        };

        $scope.networkTaxiConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: 350,
            theme: 'macarons'

        };

        $scope.popularityConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.outcomeShoppingConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.incomeShoppingConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'
        };

        $scope.wordCloud = {
            width: singleChartWidth,
            height: singleChartHeight - 5
        };

        function handleError(error) {
            console.debug(error);
        }

        PersonAreaPercentage.loadAllByPersonIdAndType({id: vm.person.id, type: 'cinema'}).$promise.then(function (areas) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(areas, function (area) {

                var number = area.percentage;
                var title = area.areaType.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.areaData = [pageload];
        }, handleError);

        PersonAreaPercentage.loadAllByPersonIdAndType({id: vm.person.id, type: 'new_media'}).$promise.then(function (areas) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(areas, function (area) {

                var number = area.percentage;
                var title = area.areaType.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.areaNewMediaData = [pageload];
        }, handleError);


        PersonInnovation.loadAllByPersonId({id: vm.person.id}).$promise.then(function (innovations) {

            $scope.innovationConfig = {
                width: singleChartWidth,
                height: singleChartHeight,
                polar: [
                    {
                        indicator: []
                    }
                ]
            };

            $scope.innovationData = [
                {
                    name: '',
                    type: 'radar',
                    data: [
                        {
                            value: [],
                            name: ''
                        }
                    ]
                }
            ];

            angular.forEach(innovations, function (innovation) {

                $scope.innovationConfig.polar[0].indicator.push({text: innovation.innovationType.name, max: 1});
                $scope.innovationData[0].data[0].value.push(innovation.percentage);
            });

        }, handleError);

        PersonExperience.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            $scope.experiences = data;
        }, handleError);

        PersonEducationBackground.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            $scope.personEducationBackgrounds = data;
        }, handleError);

        PersonPrize.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            if (data && data.length > 0) {

                $scope.prizes = lodash.groupBy(data, function (prize) {
                    return prize.prizeType.name;
                });
            }
        }, handleError);

        PersonConnectionLevel.loadAllByPersonId({id: vm.person.id}).$promise.then(function (levels) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(levels, function (level) {

                var number = level.percentage;
                var title = level.connectionLevel.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.connectionLevelData = [pageload];

        }, handleError);

        PersonPopularity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (pops) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(pops, function (pop) {

                var number = pop.percentage;
                var title = pop.popularityType.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.popularityData = [pageload];

        }, handleError);

        PersonRegionConnection.loadAllByPersonId({id: vm.person.id}).$promise.then(function (connections) {
            $scope.connections = connections;

        }, handleError);

        $scope.connectionRegionConfig = {

            height: 640,
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data:['Fans']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text:['高','低'],
                calculable : true
            }
        };

        $scope.connectionRegionData = [
            {
                name: 'Fans',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:[
                    {name: '北京',value: Math.round(Math.random()*1000)},
                    {name: '天津',value: Math.round(Math.random()*1000)},
                    {name: '上海',value: Math.round(Math.random()*1000)},
                    {name: '重庆',value: Math.round(Math.random()*1000)},
                    {name: '河北',value: Math.round(Math.random()*1000)},
                    {name: '河南',value: Math.round(Math.random()*1000)},
                    {name: '云南',value: Math.round(Math.random()*1000)},
                    {name: '辽宁',value: Math.round(Math.random()*1000)},
                    {name: '黑龙江',value: Math.round(Math.random()*1000)},
                    {name: '湖南',value: Math.round(Math.random()*1000)},
                    {name: '安徽',value: Math.round(Math.random()*1000)},
                    {name: '山东',value: Math.round(Math.random()*1000)},
                    {name: '新疆',value: Math.round(Math.random()*1000)},
                    {name: '江苏',value: Math.round(Math.random()*1000)},
                    {name: '浙江',value: Math.round(Math.random()*1000)},
                    {name: '江西',value: Math.round(Math.random()*1000)},
                    {name: '湖北',value: Math.round(Math.random()*1000)},
                    {name: '广西',value: Math.round(Math.random()*1000)},
                    {name: '甘肃',value: Math.round(Math.random()*1000)},
                    {name: '山西',value: Math.round(Math.random()*1000)},
                    {name: '内蒙古',value: Math.round(Math.random()*1000)},
                    {name: '陕西',value: Math.round(Math.random()*1000)},
                    {name: '吉林',value: Math.round(Math.random()*1000)},
                    {name: '福建',value: Math.round(Math.random()*1000)},
                    {name: '贵州',value: Math.round(Math.random()*1000)},
                    {name: '广东',value: Math.round(Math.random()*1000)},
                    {name: '青海',value: Math.round(Math.random()*1000)},
                    {name: '西藏',value: Math.round(Math.random()*1000)},
                    {name: '四川',value: Math.round(Math.random()*1000)},
                    {name: '宁夏',value: Math.round(Math.random()*1000)},
                    {name: '海南',value: Math.round(Math.random()*1000)},
                    {name: '台湾',value: Math.round(Math.random()*1000)},
                    {name: '香港',value: Math.round(Math.random()*1000)},
                    {name: '澳门',value: Math.round(Math.random()*1000)}
                ]
            }
        ];

        PersonWordCloud.loadAllByPersonId({id: vm.person.id}).$promise.then(function (words) {

            var elements = [];
            angular.forEach(words, function (word) {

                elements.push({
                    text: word.wordCloud.name,
                    size: word.count
                });
            });

            $scope.words = elements;
        }, handleError);

        PersonLawBusiness.loadAllByPersonId({id: vm.person.id}).$promise.then(function (lawBusinesses) {

            $scope.lawBusinesses = lawBusinesses;
        }, handleError);

        PersonCreditCardActivity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (creditCardActivities) {

            $scope.creditCardActivities = creditCardActivities;
        }, handleError);

        PersonNetworkDebit.loadAllByPersonId({id: vm.person.id}).$promise.then(function (networkDebits) {

            $scope.networkDebits = networkDebits;
        }, handleError);

        PersonNetworkShopping.loadAllByPersonIdAndType({id: vm.person.id, type: 'OUTCOME'}).$promise.then(function (outcomeShopping) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(outcomeShopping, function (shopping) {

                var number = shopping.amount;
                var title = shopping.description;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.outcomeShoppingData = [pageload];

        }, handleError);

        PersonNetworkShopping.loadAllByPersonIdAndType({id: vm.person.id, type: 'INCOME'}).$promise.then(function (outcomeShopping) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(outcomeShopping, function (shopping) {

                var number = shopping.amount;
                var title = shopping.description;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.incomeShoppingData = [pageload];

        }, handleError);

        PersonSocialMedia.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            if (data && data.length > 0) {

                $scope.socialMediaAttributes = lodash.groupBy(data, function (media) {
                    return media.socialMediaType.name;
                });
            }

            console.debug($scope.socialMediaAttributes);

        }, handleError);

        PersonNetworkTexiActivity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (activities) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(activities, function (activity) {

                var number = activity.count;
                var title = activity.networkTexiCompany.name;
                pageload.datapoints.push({x: title, y: number});

            });

            pageload.datapoints = lodash.sortBy(pageload.datapoints, ['y']);
            $scope.networkTexiData = [pageload];

        }, handleError);


        $scope.$on('$destroy', unsubscribe);
    }
})();
