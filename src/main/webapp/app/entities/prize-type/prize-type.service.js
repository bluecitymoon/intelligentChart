(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PrizeType', PrizeType);

    PrizeType.$inject = ['$resource'];

    function PrizeType ($resource) {
        var resourceUrl =  'api/prize-types/:id';

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
