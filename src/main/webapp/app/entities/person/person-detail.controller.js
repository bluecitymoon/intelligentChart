(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Job', 'PersonAreaPercentage', 'PersonInnovation', 'PersonExperience', 'PersonEducationBackground', 'PersonConnectionLevel', 'PersonPopularity', 'PersonPrize', 'lodash'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Job, PersonAreaPercentage, PersonInnovation, PersonExperience, PersonEducationBackground, PersonConnectionLevel, PersonPopularity, PersonPrize, lodash) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personUpdate', function(event, result) {
            vm.person = result;
        });

        $scope.areaConfig = {
            title: "",
            subtitle: '',
            height:320,
            width: 300
        };

        $scope.connectionLevelConfig = {
            title: "",
            subtitle: '',
            height:320,
            width: 350

        };

        $scope.popularityConfig = {
            title: "",
            subtitle: '',
            height:320,
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
                    pageload.datapoints.push({ x: title, y: number});

                });

                $scope.areaData = [ pageload ];
        }, handleError);

        PersonInnovation.loadAllByPersonId({id: vm.person.id}).$promise.then(function (innovations) {

            $scope.innovationConfig = {
                width: 300,
                height: 320,
                polar : [
                    {
                        indicator : []
                    }
                ]
            };

            $scope.innovationData = [
                {
                    name: '',
                    type: 'radar',
                    data : [
                        {
                            value : [],
                            name : ''
                        }
                    ]
                }
            ];

            angular.forEach(innovations, function (innovation) {

                $scope.innovationConfig.polar[0].indicator.push({ text: innovation.innovationType.name, max: 1});
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
                pageload.datapoints.push({ x: title, y: number});

            });

            // pageload.datapoints = lodash.orderBy(pageload.datapoints, ['y', 'asc']);
            $scope.connectionLevelData = [ pageload ];

        }, handleError);

        PersonPopularity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (pops) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(pops, function (pop) {

                var number = pop.percentage;
                var title = pop.popularityType.name;
                pageload.datapoints.push({ x: title, y: number});

            });

            $scope.popularityData = [ pageload ];

        }, handleError);
        function randomData() {
            return Math.round(Math.random()*1000);
        }

        $scope.connectionRegionConfig = {

            width: 600,
            height: 640,
            map: {
                mapType: 'china',
                selectedMode: 'single',
                roam: true,
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                }
            },
            theme: 'blue'


        };

        var pageload = {
            name: "",
            datapoints: []
        };

        $scope.connectionRegionData = {
            name: "",
            datapoints: [
            {x: '北京',y: randomData() },
        {x: '天津',y: randomData() },
        {x: '上海',y: randomData() },
        {x: '重庆',y: randomData() },
        {x: '河北',y: randomData() },
        {x: '河南',y: randomData() },
        {x: '云南',y: randomData() },
        {x: '辽宁',y: randomData() },
        {x: '黑龙江',y: randomData() },
        {x: '湖南',y: randomData() },
        {x: '安徽',y: randomData() },
        {x: '山东',y: randomData() },
        {x: '新疆',y: randomData() },
        {x: '江苏',y: randomData() },
        {x: '浙江',y: randomData() },
        {x: '江西',y: randomData() },
        {x: '湖北',y: randomData() },
        {x: '广西',y: randomData() },
        {x: '甘肃',y: randomData() },
        {x: '山西',y: randomData() },
        {x: '内蒙古',y: randomData() },
        {x: '陕西',y: randomData() },
        {x: '吉林',y: randomData() },
        {x: '福建',y: randomData() },
        {x: '贵州',y: randomData() },
        {x: '广东',y: randomData() },
        {x: '青海',y: randomData() },
        {x: '西藏',y: randomData() },
        {x: '四川',y: randomData() },
        {x: '宁夏',y: randomData() },
        {x: '海南',y: randomData() },
        {x: '台湾',y: randomData() },
        {x: '香港',y: randomData() },
        {x: '澳门',y: randomData() }
    ]
        };

        $scope.$on('$destroy', unsubscribe);
    }
})();
