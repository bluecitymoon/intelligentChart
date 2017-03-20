(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PopularityType', PopularityType);

    PopularityType.$inject = ['$resource'];

    function PopularityType ($resource) {
        var resourceUrl =  'api/popularity-types/:id';

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
