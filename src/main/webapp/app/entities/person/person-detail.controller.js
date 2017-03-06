(function () {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Job',
        'PersonAreaPercentage', 'PersonInnovation', 'PersonExperience', 'PersonEducationBackground', 'PersonConnectionLevel',
        'PersonPopularity', 'PersonPrize', 'lodash', 'PersonRegionConnection', 'PersonWordCloud', 'PersonLawBusiness', 'PersonCreditCardActivity'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Job, PersonAreaPercentage,
                                    PersonInnovation, PersonExperience, PersonEducationBackground, PersonConnectionLevel,
                                    PersonPopularity, PersonPrize, lodash, PersonRegionConnection, PersonWordCloud, PersonLawBusiness, PersonCreditCardActivity) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personUpdate', function (event, result) {
            vm.person = result;
        });

        $scope.areaConfig = {
            title: "",
            subtitle: '',
            height: 320,
            width: 300
        };

        $scope.connectionLevelConfig = {
            title: "",
            subtitle: '',
            height: 320,
            width: 350

        };

        $scope.popularityConfig = {
            title: "",
            subtitle: '',
            height: 320,
            width: 300
        };

        function handleError(error) {
            console.debug(error);
        }

        PersonAreaPercentage.loadAllByPersonId({id: vm.person.id}).$promise.then(function (areas) {

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

        PersonInnovation.loadAllByPersonId({id: vm.person.id}).$promise.then(function (innovations) {

            $scope.innovationConfig = {
                width: 300,
                height: 320,
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

            // pageload.datapoints = lodash.orderBy(pageload.datapoints, ['y', 'asc']);
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
        //


        $scope.wordCloud = {
            width: 300,
            height: 310
        };

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

        $scope.$on('$destroy', unsubscribe);
    }
})();
