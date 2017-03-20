(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonAreaPercentage', PersonAreaPercentage);

    PersonAreaPercentage.$inject = ['$resource'];

    function PersonAreaPercentage ($resource) {
        var resourceUrl =  'api/person-area-percentages/:id';

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
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-area-percentages/person/:id',
                params: { id : '@id'}
            },
            'loadAllByPersonIdAndType' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-area-percentages/person/:id/with/type/:type',
                params: { id : '@id', type : '@type'}
            },
            'loadAllTypesData': {
            method: 'GET',
                isArray: true,
                url: 'api/person-area-percentages/person/total/:id',
                params: { id : '@id'}
            },
            'loadAllTypesDataByMedia': {
                method: 'GET',
                isArray: true,
                url: 'api/person-area-percentages/person/total/:id/by/media',
                params: { id : '@id'}
            }
        });
    }
})();
