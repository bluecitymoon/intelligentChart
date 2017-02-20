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
            width: 300
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
            console.debug(areas);

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
                console.debug($scope.prizes);
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

        $scope.connectionRegionConfig = {

            width: 1000,
            height: 660,
            map: {
                mapType: 'china',
                selectedMode: 'single',
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                }
            },
            event: {
                type: echarts.config.EVENT.MAP_SELECTED
            }

        };

        $scope.connectionRegionData = {
            nation: [
                {name: "全国地图"}
            ],
            province: [
                {name: "省地图"}
            ]
        };

        $scope.$on('$destroy', unsubscribe);
    }
})();
