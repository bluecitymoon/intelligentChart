(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('ChartMenu', ChartMenu);

    ChartMenu.$inject = ['$resource'];

    function ChartMenu ($resource) {
        var resourceUrl =  'api/chart-menus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
