(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PrizeLevel', PrizeLevel);

    PrizeLevel.$inject = ['$resource'];

    function PrizeLevel ($resource) {
        var resourceUrl =  'api/prize-levels/:id';

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
